package dev.marcinkiewicz.graphql

internal object TestMessages {
  const val ALL_TASKS_QUERY = """{"query":"query {allTasks{id,description,completed}}"}"""
  const val INIT_WS_CONNECTION = """{"id":"0","type":"connection_init"}"""
  const val WS_ALL_TASKS_QUERY = """{"id":"1","type":"start","payload":$ALL_TASKS_QUERY}"""
}
