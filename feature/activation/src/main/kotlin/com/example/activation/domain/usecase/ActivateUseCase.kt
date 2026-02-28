package com.example.activation.domain.usecase

import com.example.activation.domain.model.Merchant
import com.example.activation.domain.repository.ActivationRepository
import javax.inject.Inject

class ActivateUseCase @Inject constructor(
    private val activationRepository: ActivationRepository
) {
    suspend operator fun invoke(): Result<Merchant> = activationRepository.activate()
}

