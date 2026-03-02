package com.example.activation.data.repository

import com.example.activation.data.mapper.toMerchant
import com.example.activation.data.model.InitializeRequest
import com.example.activation.data.remote.ActivationApiService
import com.example.common.merchant.Merchant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivationRepositoryImpl @Inject constructor(
    private val apiService: ActivationApiService
) : ActivationRepository {

    override suspend fun activate(): Result<Merchant> = runCatching {
        apiService
            .initialize(InitializeRequest(serial = "1234"))
            .toMerchant()
    }
}
