package com.example.razorpay_assignment.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.razorpay_assignment.data.TaskRepository
import com.example.razorpay_assignment.data.local.TaskEntity
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks = _tasks.asStateFlow()
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics // Initialize Firebase Analytics

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            repository.getTasks().collect { localTasks ->
                _tasks.value = localTasks
                if (localTasks.isEmpty()) { // If Room DB is empty, fetch from API
                    fetchTasksFromApi()
                }
            }
        }
    }

    private fun fetchTasksFromApi() {
        viewModelScope.launch {
            try {
                val apiTasks = repository.fetchTasksFromApi() // Fetch tasks from API
                apiTasks.forEach { task ->
                    repository.addTask(task) // Save each task to Room DB
                }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error fetching tasks from API", e)
            }
        }
    }

    fun addTask(task: TaskEntity) {
        viewModelScope.launch { repository.addTask(task) }
        logEvent("task_added", task)
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch { repository.updateTask(task) }
        logEvent("task_updated", task)
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch { repository.deleteTask(task) }
        logEvent("task_completed", task)
    }

    fun getTaskById(taskId: Int?): Flow<TaskEntity?> {
        return if (taskId == null) flowOf(null) else repository.getTaskById(taskId)
    }

    private fun logEvent(eventName: String, task: TaskEntity) {
        val bundle = Bundle().apply {
            putString("task_id", task.id.toString())
            putString("task_title", task.title)
            putString("task_description", task.description)
        }
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}
