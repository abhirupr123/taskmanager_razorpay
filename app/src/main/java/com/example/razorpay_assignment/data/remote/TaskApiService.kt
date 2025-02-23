package com.example.razorpay_assignment.data.remote

import retrofit2.http.GET
import com.example.razorpay_assignment.data.local.TaskEntity

interface TaskApiService {
    @GET("posts")
    suspend fun getTasks(): List<TaskEntity>
}
