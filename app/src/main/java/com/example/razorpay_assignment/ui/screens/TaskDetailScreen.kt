package com.example.razorpay_assignment.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.razorpay_assignment.data.local.TaskEntity
import com.example.razorpay_assignment.ui.TaskViewModel

@Composable
fun TaskDetailScreen(
    taskId: Int?,
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel() // Inject ViewModel
) {

    val context = LocalContext.current
    val isNew = taskId == 0
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val taskFlow = if (taskId != null && taskId > 0) {
        remember(taskId) { viewModel.getTaskById(taskId) }
    } else {
        null
    }
    val task by taskFlow?.collectAsState(initial = null) ?: remember { mutableStateOf(null) }


    LaunchedEffect(task) {
        task?.let {
            title=it.title
            description=it.description
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task Title") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Task Description") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (title.isNotBlank() && description.isNotBlank()) {
                val updatedTask = TaskEntity(
                    id = taskId ?: 0, // Use existing ID or let DB generate one
                    title = title,
                    description = description,
                    pending = task?.pending ?: false
                )
                if (task == null) {
                    viewModel.addTask(updatedTask) // Create new task
                    Toast.makeText(context, "Task added successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.updateTask(updatedTask) // Update existing task
                    Toast.makeText(context, "Task updated successfully!", Toast.LENGTH_SHORT).show()
                }
            }
            navController.popBackStack() // Navigate back
        }) {
            Text(if (task == null) "Add Task" else "Update Task")
        }
    }
}
