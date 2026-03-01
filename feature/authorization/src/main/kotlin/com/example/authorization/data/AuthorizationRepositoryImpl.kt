package com.example.authorization.data

import com.example.common.transaction.Transaction
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationRepositoryImpl @Inject constructor() : AuthorizationRepository {

    override suspend fun processPayment(transaction: Transaction): Result<Transaction> {
        delay(2000L)
        return Result.success(transaction.copy(id = "1234"))
    }
}

