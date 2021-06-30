package dev.marcinkiewicz.graphql.core.spi

import dev.marcinkiewicz.graphql.core.task.Task
import graphql.schema.DataFetchingEnvironment

interface TaskService {
  fun allTasks(env: DataFetchingEnvironment): List<Task>
  fun complete(env: DataFetchingEnvironment): Boolean
}
