package dev.marcinkiewicz.graphql.tasks.adapters.taskservice

import dev.marcinkiewicz.graphql.tasks.domain.model.Task
import dev.marcinkiewicz.graphql.tasks.domain.ports.TaskRepository
import dev.marcinkiewicz.graphql.tasks.domain.ports.TaskService

class TaskServiceImpl(private val taskRepository: TaskRepository) : TaskService {
  override fun allTasks(uncompletedOnly: Boolean): Set<Task> = taskRepository.getAllTasks(uncompletedOnly)
  override fun complete(id: String): Boolean = taskRepository.completeTask(id)
}
