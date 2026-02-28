package com.example.activation.domain.repository

import com.example.activation.domain.model.Merchant

interface ActivationRepository {
    suspend fun activate(): Result<Merchant>
}

