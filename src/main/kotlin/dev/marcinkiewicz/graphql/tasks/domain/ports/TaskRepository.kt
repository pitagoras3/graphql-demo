package dev.marcinkiewicz.graphql.tasks.domain.ports

import dev.marcinkiewicz.graphql.tasks.domain.model.Task

interface TaskRepository {
  fun getAllTasks(uncompletedOnly: Boolean): Set<Task>
  fun completeTask(id: String): Boolean
}
