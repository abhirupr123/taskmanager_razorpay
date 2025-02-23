package com.example.razorpay_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.razorpay_assignment.data.Screen
import com.example.razorpay_assignment.ui.TaskViewModel
import com.example.razorpay_assignment.ui.screens.TaskDetailScreen
import com.example.razorpay_assignment.ui.screens.TaskListScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: TaskViewModel = hiltViewModel()
            NavHost(navController, startDestination = Screen.TaskList.route) {
                composable(Screen.TaskList.route) {
                    TaskListScreen(
                        viewModel = viewModel,
                        onTaskClick = {
                            task->navController.navigate(Screen.TaskDetail.createRoute(task.id))
                        }
                    )
                }
                composable(Screen.TaskDetail.route) { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                    TaskDetailScreen(taskId, navController)
                }
            }
        }
    }
}
