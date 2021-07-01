package dev.marcinkiewicz.graphql.tasks.adapters.taskdb

import dev.marcinkiewicz.graphql.tasks.domain.model.Task
import dev.marcinkiewicz.graphql.tasks.domain.ports.TaskRepository

internal class InMemoryTaskRepository : TaskRepository {

  private val db: MutableMap<String, Task> by lazy { mockedTasks().toMutableMap() }

  override fun getAllTasks(uncompletedOnly: Boolean): Set<Task> = db.values
    .filter { if (uncompletedOnly) !it.completed else true }
    .toSet()

  override fun completeTask(id: String): Boolean =
    db[id]?.let {
      db[id] = it.copy(completed = true)
      true
    } ?: false
}
