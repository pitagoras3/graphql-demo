package dev.marcinkiewicz.graphql.infrastructure.graphql

import dev.marcinkiewicz.graphql.core.spi.TaskService
import dev.marcinkiewicz.graphql.infrastructure.graphql.Operation.MUTATION
import dev.marcinkiewicz.graphql.infrastructure.graphql.Operation.QUERY
import graphql.schema.idl.RuntimeWiring

object GraphQlApi {
  fun runtimeWiring(taskService: TaskService): RuntimeWiring = RuntimeWiring.newRuntimeWiring()
    .type(QUERY.value) { it.dataFetcher("allTasks", taskService::allTasks) }
    .type(MUTATION.value) { it.dataFetcher("complete", taskService::complete) }
    .build()
}
