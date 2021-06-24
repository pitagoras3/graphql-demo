package dev.marcinkiewicz.graphql.task

import java.util.*

data class Task(
  val description: String,
  val id: String = UUID.randomUUID().toString(),
  val completed: Boolean = false,
)
