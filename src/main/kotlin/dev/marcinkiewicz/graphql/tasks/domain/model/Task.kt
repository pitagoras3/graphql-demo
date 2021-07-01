package dev.marcinkiewicz.graphql.tasks.domain.model

data class Task(
  val description: String,
  val id: String,
  val completed: Boolean,
)
