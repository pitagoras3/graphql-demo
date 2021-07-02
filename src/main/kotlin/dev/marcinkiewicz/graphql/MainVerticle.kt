package dev.marcinkiewicz.graphql

import dev.marcinkiewicz.graphql.tasks.adapters.api.TaskFacade
import dev.marcinkiewicz.graphql.tasks.adapters.api.applicationRouter
import dev.marcinkiewicz.graphql.tasks.adapters.taskdb.InMemoryTaskRepository
import dev.marcinkiewicz.graphql.tasks.adapters.taskservice.TaskServiceImpl
import dev.marcinkiewicz.graphql.tasks.config.graphql.GraphQlConfiguration.setupGraphQlSchemaAndWiring
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.handler.graphql.ApolloWSHandler
import io.vertx.ext.web.handler.graphql.GraphQLHandler
import io.vertx.ext.web.handler.graphql.GraphiQLHandler
import io.vertx.kotlin.ext.web.handler.graphql.graphiQLHandlerOptionsOf

class MainVerticle : AbstractVerticle() {

  override fun start() {
    val graphQl = setupGraphQlSchemaAndWiring(
      vertx,
      TaskFacade(TaskServiceImpl(InMemoryTaskRepository())) // Dependency injection at its finest :)
    )

    val graphQlHandler: GraphQLHandler = GraphQLHandler.create(graphQl)
    val wsHandler = ApolloWSHandler.create(graphQl)
    val graphiQlHandler: GraphiQLHandler = GraphiQLHandler.create(
      graphiQLHandlerOptionsOf(
        enabled = true,
        graphQLUri = "/graphql"
      )
    )

    vertx.createHttpServer(HttpServerOptions().addWebSocketSubProtocol("graphql-ws"))
      .requestHandler(applicationRouter(vertx, graphQlHandler, graphiQlHandler, wsHandler))
      .listen(8080)
  }
}
