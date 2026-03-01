package com.example.authorization.ui

import com.example.common.currency.Currency
import com.example.common.transaction.Transaction

// ─── State ───────────────────────────────────────────────────────────────────
data class AuthorizationState(
    val currency: Currency = Currency.PEN,
    val amount: String = "",
    val tipAmount: String = "",
    val isProcessing: Boolean = false,
    val errorMessage: String? = null,
    val approvedPayment: Transaction? = null
)

// ─── Intents ─────────────────────────────────────────────────────────────────
sealed class AuthorizationIntent {
    data class OnCurrencyChanged(val currency: Currency) : AuthorizationIntent()
    data class OnAmountChanged(val amount: String) : AuthorizationIntent()
    data class OnTipAmountChanged(val tipAmount: String) : AuthorizationIntent()
    data object OnPaymentEntrySubmitted : AuthorizationIntent()
    data object OnCardRead : AuthorizationIntent()
    data object OnSignatureConfirmed : AuthorizationIntent()
    data object OnProcessingCompleted : AuthorizationIntent()
    data object OnDone : AuthorizationIntent()
}

// ─── Side Effects ─────────────────────────────────────────────────────────────
sealed class AuthorizationSideEffect {
    data object NavigateToReadCard : AuthorizationSideEffect()
    data object NavigateToSignature : AuthorizationSideEffect()
    data object NavigateToProcessing : AuthorizationSideEffect()
    data object NavigateToConfirmation : AuthorizationSideEffect()
    data object NavigateBack : AuthorizationSideEffect()
    data class ShowError(val message: String) : AuthorizationSideEffect()
}

