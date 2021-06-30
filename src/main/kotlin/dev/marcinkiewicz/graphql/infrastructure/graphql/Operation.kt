package dev.marcinkiewicz.graphql.infrastructure.graphql

enum class Operation(val value: String) {
  QUERY("Query"),
  MUTATION("Mutation")
}
