package com.example.activation.data.repository

import com.example.activation.domain.model.Merchant
import com.example.activation.domain.repository.ActivationRepository
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivationRepositoryImpl @Inject constructor() : ActivationRepository {

    override suspend fun activate(): Result<Merchant> {
        delay(1500L)
        return Result.success(
            Merchant(
                id = UUID.randomUUID().toString(),
                name = "Culqi Merchant"
            )
        )
    }
}

