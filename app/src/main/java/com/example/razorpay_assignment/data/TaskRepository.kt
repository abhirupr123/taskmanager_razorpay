package com.example.razorpay_assignment.data

import com.example.razorpay_assignment.data.local.TaskDao
import com.example.razorpay_assignment.data.local.TaskEntity
import com.example.razorpay_assignment.data.remote.TaskApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskApiService: TaskApiService
) {
    fun getTasks(): Flow<List<TaskEntity>> = taskDao.getTasks()

    suspend fun fetchTasksFromApi(): List<TaskEntity> {
        return taskApiService.getTasks()
    }

    suspend fun addTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    fun getTaskById(taskId: Int): Flow<TaskEntity?> {
        return taskDao.getTaskById(taskId)
    }

}
