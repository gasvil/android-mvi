package com.example.activation.data

import com.example.common.merchant.Merchant

interface ActivationRepository {
    suspend fun activate(): Result<Merchant>
}