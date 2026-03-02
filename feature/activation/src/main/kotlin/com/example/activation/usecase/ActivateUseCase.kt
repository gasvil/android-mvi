package com.example.activation.usecase

import com.example.common.merchant.Merchant
import com.example.activation.data.repository.ActivationRepository
import javax.inject.Inject

class ActivateUseCase @Inject constructor(
    private val activationRepository: ActivationRepository
) {
    suspend operator fun invoke(): Result<Merchant> = activationRepository.activate()
}

