package dev.marcinkiewicz.graphql.tasks.domain.ports

import dev.marcinkiewicz.graphql.tasks.domain.model.Task

interface TaskService {
  fun allTasks(uncompletedOnly: Boolean): Set<Task>
  fun complete(id: String): Boolean
}
