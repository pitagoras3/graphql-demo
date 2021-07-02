package dev.marcinkiewicz.graphql

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.marcinkiewicz.graphql.TestMessages.ALL_TASKS_QUERY
import io.reactiverse.junit5.web.TestRequest.statusCode
import io.reactiverse.junit5.web.TestRequest.testRequest
import io.reactiverse.junit5.web.WebClientOptionsInject
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Test

internal class GraphQlHttpTest : MainVerticleConfiguration {

  @WebClientOptionsInject
  @JvmField
  val options = WebClientOptions()
    .setDefaultHost("localhost")
    .setDefaultPort(8080)

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
}
