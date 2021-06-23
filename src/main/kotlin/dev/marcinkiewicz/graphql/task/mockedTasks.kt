package dev.marcinkiewicz.graphql.task

fun mockedTasks(): Map<String, Task> = setOf(
  Task("Task1"),
  Task("Task2"),
  Task("Task3")
).associateBy(Task::id)
