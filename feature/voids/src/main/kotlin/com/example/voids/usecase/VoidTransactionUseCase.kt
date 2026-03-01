package com.example.voids.usecase

import com.example.common.transaction.Transaction
import com.example.voids.data.VoidRepository
import javax.inject.Inject

class VoidTransactionUseCase @Inject constructor(
    private val voidRepository: VoidRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Transaction> =
        voidRepository.voidTransaction(transaction)
}


