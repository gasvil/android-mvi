package com.example.authorization.data

import com.example.common.transaction.Transaction

interface AuthorizationRepository {
    suspend fun processPayment(transaction: Transaction): Result<Transaction>
}