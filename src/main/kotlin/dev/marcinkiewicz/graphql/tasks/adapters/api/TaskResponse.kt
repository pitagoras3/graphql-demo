package dev.marcinkiewicz.graphql.tasks.adapters.api

import dev.marcinkiewicz.graphql.tasks.domain.model.Task

internal data class TaskResponse(
  val description: String,
  val id: String,
  val completed: Boolean,
) {
  companion object {
    fun of(task: Task) = TaskResponse(task.description, task.id, task.completed)
  }
}
