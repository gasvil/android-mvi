package com.example.voids.data

import com.example.common.transaction.Transaction
import com.example.voids.data.VoidRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoidRepositoryImpl @Inject constructor() : VoidRepository {

    override suspend fun voidTransaction(transaction: Transaction): Result<Transaction> {
        delay(2000L)
        return Result.success(transaction)
    }
}


