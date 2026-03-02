package com.example.activation.data.model

import com.google.gson.annotations.SerializedName

data class InitializeRequest(
    @SerializedName("serial") val serial: String
)

