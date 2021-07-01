package dev.marcinkiewicz.graphql.tasks.adapters.api

import dev.marcinkiewicz.graphql.tasks.domain.ports.TaskService
import graphql.schema.DataFetchingEnvironment

internal class TaskFacade(private val taskService: TaskService) {

  internal fun findAll(env: DataFetchingEnvironment): Set<TaskResponse> {
    val uncompletedOnly: Boolean = env.getArgument("uncompletedOnly")
    return taskService.allTasks(uncompletedOnly).map(TaskResponse::of).toSet()
  }

  internal fun complete(env: DataFetchingEnvironment): Boolean {
    val id: String = env.getArgument("id")
    return taskService.complete(id)
  }
}
