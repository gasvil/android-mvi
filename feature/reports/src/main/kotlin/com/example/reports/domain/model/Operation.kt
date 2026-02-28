package com.example.reports.domain.model

enum class OperationType { CREDIT, DEBIT, TRANSFER }

data class Operation(
    val id: String,
    val title: String,
    val description: String,
    val amount: Double,
    val type: OperationType,
    val dateMillis: Long
)

