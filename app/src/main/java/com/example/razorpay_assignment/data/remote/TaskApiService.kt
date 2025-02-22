package com.example.razorpay_assignment.data.remote

import retrofit2.http.GET
import com.example.razorpay_assignment.data.local.TaskEntity

interface TaskApiService {
    @GET("263f922d-96f7-4228-8723-3b555acca230")
    suspend fun getTasks(): List<TaskEntity>
}
