package dev.marcinkiewicz.graphql.tasks.config.graphql

internal enum class Operation(val value: String) {
  QUERY("Query"),
  MUTATION("Mutation")
}
