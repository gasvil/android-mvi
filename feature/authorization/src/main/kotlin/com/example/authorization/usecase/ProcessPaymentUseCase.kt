package com.example.authorization.usecase

import com.example.authorization.data.AuthorizationRepository
import com.example.common.transaction.Transaction
import javax.inject.Inject

class ProcessPaymentUseCase @Inject constructor(
    private val authorizationRepository: AuthorizationRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Transaction> =
        authorizationRepository.processPayment(transaction)
}

