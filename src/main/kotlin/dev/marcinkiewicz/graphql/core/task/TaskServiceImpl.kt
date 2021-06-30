package dev.marcinkiewicz.graphql.core.task

import dev.marcinkiewicz.graphql.core.spi.TaskService
import graphql.schema.DataFetchingEnvironment

class TaskServiceImpl : TaskService {

  private val tasks by lazy { mockedTasks().toMutableMap() }

  override fun allTasks(env: DataFetchingEnvironment): List<Task> {
    val uncompletedOnly: Boolean = env.getArgument("uncompletedOnly")
    return tasks.values.filter { if (uncompletedOnly) !it.completed else true }
  }

  override fun complete(env: DataFetchingEnvironment): Boolean {
    val id: String = env.getArgument("id")
    val task = tasks[id]
    return if(task != null) {
      tasks[id] = task.copy(completed = true)
      true
    }
    else false
  }
}
