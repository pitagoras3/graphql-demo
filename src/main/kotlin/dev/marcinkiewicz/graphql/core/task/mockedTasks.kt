package dev.marcinkiewicz.graphql.core.task

fun mockedTasks(): Map<String, Task> = setOf(
  Task("Task1"),
  Task("Task2"),
  Task("Task3")
).associateBy(Task::id)
