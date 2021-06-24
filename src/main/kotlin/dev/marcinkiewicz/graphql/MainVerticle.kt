package dev.marcinkiewicz.graphql

import dev.marcinkiewicz.graphql.infrastructure.GraphQlConfiguration
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.graphql.GraphQLHandler
import io.vertx.ext.web.handler.graphql.GraphiQLHandler
import io.vertx.ext.web.handler.graphql.GraphiQLHandlerOptions
import io.vertx.kotlin.ext.web.handler.graphql.graphiQLHandlerOptionsOf

class MainVerticle : AbstractVerticle() {

  override fun start() {
    val graphQlConfiguration = GraphQlConfiguration(vertx)
    val graphQlHandler = GraphQLHandler.create(graphQlConfiguration.setupGraphQl())
    val graphiQLHandlerOptions: GraphiQLHandlerOptions = graphiQLHandlerOptionsOf(
      enabled = true,
      graphQLUri = "/graphql"
    )
    val router = Router.router(vertx)

    router.route().handler(BodyHandler.create())
    router.route("/graphql").handler(graphQlHandler)
    router.route("/graphiql/*").handler(GraphiQLHandler.create(graphiQLHandlerOptions))

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
  }
}
