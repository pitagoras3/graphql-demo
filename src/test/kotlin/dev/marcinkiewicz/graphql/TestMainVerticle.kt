package dev.marcinkiewicz.graphql

import assertk.assertThat
import assertk.assertions.isEqualTo
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
  fun `should return 10 tasks when starting application`(testContext: VertxTestContext, client: WebClient) {
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
      .sendJson(JsonObject("""{"query":"query {allTasks{id,description,completed}}"}"""), testContext)
  }

  @Test
  fun verticleDeployed(vertx: Vertx, testContext: VertxTestContext) {
    testContext.completeNow()
  }
}
