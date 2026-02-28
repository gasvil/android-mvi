package com.example.reports.domain.repository

import com.example.reports.domain.model.Operation

interface ReportsRepository {
    suspend fun getOperations(): Result<List<Operation>>
}

