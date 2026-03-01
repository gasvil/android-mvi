package com.example.voids.data

import com.example.common.transaction.Transaction

interface VoidRepository {
    suspend fun voidTransaction(transaction: Transaction): Result<Transaction>
}