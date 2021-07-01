package dev.marcinkiewicz.graphql.tasks.adapters.api

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.graphql.GraphQLHandler
import io.vertx.ext.web.handler.graphql.GraphiQLHandler

internal fun applicationRouter(
  vertx: Vertx,
  graphQlHandler: GraphQLHandler,
  graphiQlHandler: GraphiQLHandler
): Router = Router.router(vertx).apply {
  mountSubRouter("/", graphQlRouter(vertx, graphQlHandler))
  mountSubRouter("/", graphiQlRouter(vertx, graphiQlHandler))
}

private fun graphQlRouter(vertx: Vertx, graphQlHandler: GraphQLHandler): Router =
  Router.router(vertx).apply {
    route().handler(BodyHandler.create())
    route("/graphql").handler(graphQlHandler)
  }

private fun graphiQlRouter(vertx: Vertx, graphiQLHandler: GraphiQLHandler): Router =
  Router.router(vertx).apply {
    route("/graphiql/*").handler(graphiQLHandler)
  }
