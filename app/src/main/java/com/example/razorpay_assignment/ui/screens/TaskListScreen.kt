package com.example.razorpay_assignment.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.razorpay_assignment.ui.TaskViewModel
import com.example.razorpay_assignment.data.local.TaskEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = viewModel(),
    onTaskClick: (TaskEntity) -> Unit,
    navController: NavController
) {
    val tasks by viewModel.tasks.collectAsState(emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Task Manager") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("task_detail/0")  }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = { onTaskClick(task) }, // Navigate to edit screen
                    onCompleteClick = { updatedTask ->
                        viewModel.updateTask(updatedTask) // Mark task as completed
                    },
                    onDeleteClick = { viewModel.deleteTask(task) } // Delete task
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: TaskEntity,
    onTaskClick: (TaskEntity) -> Unit,
    onCompleteClick: (TaskEntity) -> Unit,
    onDeleteClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onTaskClick(task) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, fontWeight = FontWeight.Bold)
                Text(text = task.description)
            }
            Checkbox(
                checked = task.pending,
                onCheckedChange = {
                    val updatedTask = task.copy(pending = it)
                    onCompleteClick(updatedTask) // Update task status
                }
            )
            IconButton(onClick = {
                onDeleteClick()
                Toast.makeText(context, "Task completed successfully!", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
