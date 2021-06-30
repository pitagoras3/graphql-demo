package dev.marcinkiewicz.graphql.infrastructure

import dev.marcinkiewicz.graphql.core.task.Task
import dev.marcinkiewicz.graphql.core.task.mockedTasks
import graphql.GraphQL
import graphql.schema.DataFetchingEnvironment
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeRuntimeWiring
import io.vertx.core.Vertx

class GraphQlConfiguration(
  private val vertx: Vertx
) {

  private val tasks by lazy { mockedTasks().toMutableMap() }

  fun setupGraphQl(): GraphQL {
    val schema = vertx.fileSystem().readFileBlocking("tasks.graphqls").toString()
    val schemaParser = SchemaParser()
    val typeDefinitionRegistry = schemaParser.parse(schema)
    val runtimeWiring = RuntimeWiring.newRuntimeWiring()
      .type(TypeRuntimeWiring.newTypeWiring("a"))
      .type("Query") { it.dataFetcher("allTasks", this::allTasks) }
      .type("Mutation") { it.dataFetcher("complete", this::complete) }
      .build()

    val schemaGenerator = SchemaGenerator()
    val graphQlSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring)

    return GraphQL.newGraphQL(graphQlSchema).build()
  }

  private fun allTasks(env: DataFetchingEnvironment): List<Task> {
    val uncompletedOnly: Boolean = env.getArgument("uncompletedOnly")
    return tasks.values.filter { if (uncompletedOnly) !it.completed else true }
  }

  private fun complete(env: DataFetchingEnvironment): Boolean {
    val id: String = env.getArgument("id")
    val task = tasks[id]
    return if(task != null) {
      tasks[id] = task.copy(completed = true)
      true
    }
    else false
  }
}
