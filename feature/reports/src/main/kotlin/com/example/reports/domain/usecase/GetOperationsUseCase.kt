package com.example.reports.domain.usecase

import com.example.reports.domain.model.Operation
import com.example.reports.domain.repository.ReportsRepository
import javax.inject.Inject

class GetOperationsUseCase @Inject constructor(
    private val reportsRepository: ReportsRepository
) {
    suspend operator fun invoke(): Result<List<Operation>> =
        reportsRepository.getOperations()
}

