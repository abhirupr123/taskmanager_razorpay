package com.example.razorpay_assignment.data

sealed class Screen(val route: String) {
    object TaskList : Screen("task_list")
    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: Int?) = "task_detail/$taskId"
    }
}
