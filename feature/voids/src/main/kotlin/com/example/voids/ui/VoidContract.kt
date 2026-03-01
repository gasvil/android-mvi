package com.example.voids.ui

import com.example.common.transaction.Transaction

// ─── State ───────────────────────────────────────────────────────────────────
data class VoidState(
    val searchQuery: String = "",
    val selectedTransaction: Transaction? = null,
    val isProcessing: Boolean = false,
    val errorMessage: String? = null,
    val voidedTransaction: Transaction? = null
)

// ─── Intents ─────────────────────────────────────────────────────────────────
sealed class VoidIntent {
    data class OnSearchQueryChanged(val query: String) : VoidIntent()
    data class OnTransactionSelected(val transaction: Transaction) : VoidIntent()
    data object OnVoidConfirmed : VoidIntent()
    data object OnProcessingCompleted : VoidIntent()
    data object OnDone : VoidIntent()
}

// ─── Side Effects ─────────────────────────────────────────────────────────────
sealed class VoidSideEffect {
    data object NavigateToProcessing : VoidSideEffect()
    data object NavigateToConfirmation : VoidSideEffect()
    data object NavigateBack : VoidSideEffect()
    data class ShowError(val message: String) : VoidSideEffect()
}


