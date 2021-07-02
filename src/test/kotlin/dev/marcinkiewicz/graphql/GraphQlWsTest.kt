package dev.marcinkiewicz.graphql

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.marcinkiewicz.graphql.TestMessages.INIT_WS_CONNECTION
import dev.marcinkiewicz.graphql.TestMessages.WS_ALL_TASKS_QUERY
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Test

internal class GraphQlWsTest : MainVerticleConfiguration {

  @Test
  fun `should open websocket connection`(vertx: Vertx, testContext: VertxTestContext) {
    val client = vertx.createHttpClient()
    testContext.assertComplete(client.webSocket(8080, "localhost", "/graphql-ws"))
      .onComplete { testContext.completeNow() }
  }

  @Test
  fun `should return 10 tasks through webSocket`(vertx: Vertx, testContext: VertxTestContext) {
    val client = vertx.createHttpClient()
    client.webSocket(8080, "localhost", "/graphql-ws") {
      val ws = it.result()
      ws.write(JsonObject(INIT_WS_CONNECTION).toBuffer()).onComplete {
        ws.write(JsonObject(WS_ALL_TASKS_QUERY).toBuffer())
      }
      ws.handler { buffer ->
        val wsResponse = buffer.toJsonObject()
        if (wsResponse.getString("id") == "1" && wsResponse.getString("type") == "data") {
          assertThat(
            wsResponse
              .getJsonObject("payload")
              .getJsonObject("data")
              .getJsonArray("allTasks")
              .size()
          ).isEqualTo(10)
          testContext.completeNow()
        }
      }
    }
  }
}
