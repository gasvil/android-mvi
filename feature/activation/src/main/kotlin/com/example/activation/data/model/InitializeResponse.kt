package com.example.activation.data.model

import com.google.gson.annotations.SerializedName

data class InitializeResponse(
    @SerializedName("initialized") val initialized: Boolean,
    @SerializedName("token")       val token: String,
    @SerializedName("name")        val name: String,
    @SerializedName("id")          val id: String
)

