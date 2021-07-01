package dev.marcinkiewicz.graphql.tasks.config.graphql

import dev.marcinkiewicz.graphql.tasks.adapters.api.GraphQlApi
import dev.marcinkiewicz.graphql.tasks.adapters.api.TaskFacade
import graphql.GraphQL
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import io.vertx.core.Vertx

internal object GraphQlConfiguration {

  private const val GRAPHQL_SCHEMA_FILE = "tasks.graphqls"

  internal fun setupGraphQlSchemaAndWiring(vertx: Vertx, taskFacade: TaskFacade): GraphQL {
    val graphQlSchema = SchemaGenerator().makeExecutableSchema(
      SchemaParser().parse(schemaFromFile(vertx, GRAPHQL_SCHEMA_FILE)),
      GraphQlApi.runtimeWiring(taskFacade)
    )

    return GraphQL.newGraphQL(graphQlSchema).build()
  }

  private fun schemaFromFile(vertx: Vertx, fileName: String) =
    vertx.fileSystem().readFileBlocking(fileName).toString()
}
