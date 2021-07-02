package dev.marcinkiewicz.graphql

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.marcinkiewicz.graphql.TestMainVerticle.TestMessage.ALL_TASKS_QUERY
import dev.marcinkiewicz.graphql.TestMainVerticle.TestMessage.INIT_WS_CONNECTION
import dev.marcinkiewicz.graphql.TestMainVerticle.TestMessage.WS_QUERY
import io.reactiverse.junit5.web.TestRequest.*
import io.reactiverse.junit5.web.WebClientOptionsInject
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class TestMainVerticle {

  @WebClientOptionsInject
  @JvmField
  val options = WebClientOptions()
    .setDefaultHost("localhost")
    .setDefaultPort(8080)

  @BeforeEach
  fun deployVerticle(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(MainVerticle(), testContext.succeedingThenComplete())
  }

  @Test
  fun `should return 10 tasks through http`(testContext: VertxTestContext, client: WebClient) {
    testRequest(client, HttpMethod.POST, "/graphql")
      .expect(
        statusCode(200), {
          assertThat(
            it.bodyAsJsonObject()
              .getJsonObject("data")
              .getJsonArray("allTasks")
              .size()
          ).isEqualTo(10)
        })
      .sendJson(JsonObject(ALL_TASKS_QUERY), testContext)
  }

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
        ws.write(JsonObject(WS_QUERY).toBuffer())
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

  @Test
  fun verticleDeployed(testContext: VertxTestContext) {
    testContext.completeNow()
  }

  private object TestMessage {
    const val ALL_TASKS_QUERY = """{"query":"query {allTasks{id,description,completed}}"}"""
    const val INIT_WS_CONNECTION = """{"id": "0","type": "connection_init"}"""
    const val WS_QUERY = """{"id": "1","type": "start","payload": $ALL_TASKS_QUERY}"""
  }
}
