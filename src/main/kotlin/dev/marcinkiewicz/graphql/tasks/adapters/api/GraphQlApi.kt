package dev.marcinkiewicz.graphql.tasks.adapters.api

import dev.marcinkiewicz.graphql.tasks.config.graphql.Operation.MUTATION
import dev.marcinkiewicz.graphql.tasks.config.graphql.Operation.QUERY
import graphql.schema.idl.RuntimeWiring

internal object GraphQlApi {

  internal fun runtimeWiring(taskFacade: TaskFacade): RuntimeWiring = RuntimeWiring.newRuntimeWiring()
    .type(QUERY.value) { it.dataFetcher("allTasks", taskFacade::findAll) }
    .type(MUTATION.value) { it.dataFetcher("complete", taskFacade::complete) }
    .build()
}
