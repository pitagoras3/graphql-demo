package dev.marcinkiewicz.graphql.tasks.adapters.taskdb

import dev.marcinkiewicz.graphql.tasks.domain.model.Task
import java.util.*

private const val MOCKED_INITIAL_SIZE = 10

internal fun mockedTasks(): Map<String, Task> =
  List(MOCKED_INITIAL_SIZE) { createMockedTask("Task [$it]") }.associateBy(Task::id)

private fun createMockedTask(description: String) = Task(
  description,
  UUID.randomUUID().toString(),
  false
)
