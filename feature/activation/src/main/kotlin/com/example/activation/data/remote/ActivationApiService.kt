package com.example.activation.data.remote

import com.example.activation.data.model.InitializeRequest
import com.example.activation.data.model.InitializeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ActivationApiService {

    @POST("api/initialize")
    suspend fun initialize(
        @Body request: InitializeRequest
    ): InitializeResponse
}

