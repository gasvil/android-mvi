package com.example.reports.ui

import com.example.reports.domain.model.Operation

// ─── State ───────────────────────────────────────────────────────────────────
data class ReportsState(
    val operations: List<Operation> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

// ─── Intents ─────────────────────────────────────────────────────────────────
sealed class ReportsIntent {
    data object LoadOperations : ReportsIntent()
    data object RetryClicked : ReportsIntent()
}

// ─── Side Effects ─────────────────────────────────────────────────────────────
sealed class ReportsSideEffect {
    data class ShowToastError(val message: String) : ReportsSideEffect()
}

