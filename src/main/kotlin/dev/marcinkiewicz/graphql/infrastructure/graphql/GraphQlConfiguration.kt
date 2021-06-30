package dev.marcinkiewicz.graphql.infrastructure.graphql

import dev.marcinkiewicz.graphql.core.spi.TaskService
import graphql.GraphQL
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import io.vertx.core.Vertx

class GraphQlConfiguration(private val vertx: Vertx) {

  fun setupGraphQl(taskService: TaskService): GraphQL {
    val schemaString = vertx.fileSystem().readFileBlocking("tasks.graphqls").toString()
    val graphQlSchema = SchemaGenerator().makeExecutableSchema(
      SchemaParser().parse(schemaString),
      GraphQlApi.runtimeWiring(taskService)
    )

    return GraphQL.newGraphQL(graphQlSchema).build()
  }
}
