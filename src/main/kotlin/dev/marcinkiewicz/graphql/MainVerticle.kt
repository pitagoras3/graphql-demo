package dev.marcinkiewicz.graphql

import dev.marcinkiewicz.graphql.tasks.adapters.api.TaskFacade
import dev.marcinkiewicz.graphql.tasks.adapters.api.applicationRouter
import dev.marcinkiewicz.graphql.tasks.adapters.taskdb.InMemoryTaskRepository
import dev.marcinkiewicz.graphql.tasks.adapters.taskservice.TaskServiceImpl
import dev.marcinkiewicz.graphql.tasks.config.graphql.GraphQlConfiguration.setupGraphQlSchemaAndWiring
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.handler.graphql.GraphQLHandler
import io.vertx.ext.web.handler.graphql.GraphiQLHandler
import io.vertx.kotlin.ext.web.handler.graphql.graphiQLHandlerOptionsOf

class MainVerticle : AbstractVerticle() {

  override fun start() {
    val graphQlHandler: GraphQLHandler =
      GraphQLHandler.create(
        setupGraphQlSchemaAndWiring(
          vertx,
          TaskFacade(TaskServiceImpl(InMemoryTaskRepository())) // Dependency injection at its finest :)
        )
      )

    val graphiQlHandler: GraphiQLHandler = GraphiQLHandler.create(
      graphiQLHandlerOptionsOf(
        enabled = true,
        graphQLUri = "/graphql"
      )
    )

    vertx.createHttpServer()
      .requestHandler(applicationRouter(vertx, graphQlHandler, graphiQlHandler))
      .listen(8080)
  }
}
