package dev.marcinkiewicz.graphql.tasks.adapters.api

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.graphql.ApolloWSHandler
import io.vertx.ext.web.handler.graphql.GraphQLHandler
import io.vertx.ext.web.handler.graphql.GraphiQLHandler

internal fun applicationRouter(
  vertx: Vertx,
  graphQlHandler: GraphQLHandler,
  graphiQlHandler: GraphiQLHandler,
  wsHandler: ApolloWSHandler
): Router = Router.router(vertx).apply {
  route().handler(BodyHandler.create())
  route("/graphql").handler(graphQlHandler)
  route("/graphiql/*").handler(graphiQlHandler)
  route("/graphql-ws").handler(wsHandler)
}
