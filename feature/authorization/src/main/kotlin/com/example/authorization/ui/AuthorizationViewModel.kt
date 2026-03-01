package com.example.authorization.ui

import androidx.lifecycle.ViewModel
import com.example.authorization.usecase.ProcessPaymentUseCase
import com.example.common.currency.Currency
import com.example.common.transaction.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val processPaymentUseCase: ProcessPaymentUseCase
) : ViewModel(), ContainerHost<AuthorizationState, AuthorizationSideEffect> {

    override val container = container<AuthorizationState, AuthorizationSideEffect>(AuthorizationState())

    fun handleIntent(intent: AuthorizationIntent) {
        when (intent) {
            is AuthorizationIntent.OnCurrencyChanged -> onCurrencyChanged(intent.currency)
            is AuthorizationIntent.OnAmountChanged -> onAmountChanged(intent.amount)
            is AuthorizationIntent.OnTipAmountChanged -> onTipAmountChanged(intent.tipAmount)
            is AuthorizationIntent.OnPaymentEntrySubmitted -> onPaymentEntrySubmitted()
            is AuthorizationIntent.OnCardRead -> onCardRead()
            is AuthorizationIntent.OnSignatureConfirmed -> onSignatureConfirmed()
            is AuthorizationIntent.OnProcessingCompleted -> onProcessingCompleted()
            is AuthorizationIntent.OnDone -> onDone()
        }
    }

    private fun onCurrencyChanged(currency: Currency) = intent {
        reduce { state.copy(currency = currency) }
    }

    private fun onAmountChanged(amount: String) = intent {
        reduce { state.copy(amount = amount) }
    }

    private fun onTipAmountChanged(tipAmount: String) = intent {
        reduce { state.copy(tipAmount = tipAmount) }
    }

    private fun onPaymentEntrySubmitted() = intent {
        postSideEffect(AuthorizationSideEffect.NavigateToReadCard)
    }

    private fun onCardRead() = intent {
        postSideEffect(AuthorizationSideEffect.NavigateToSignature)
    }

    private fun onSignatureConfirmed() = intent {
        postSideEffect(AuthorizationSideEffect.NavigateToProcessing)
    }

    private fun onProcessingCompleted() = intent {
        reduce { state.copy(isProcessing = true, errorMessage = null) }
        val payment = Transaction(
            amount = state.amount.toDoubleOrNull() ?: 0.0,
            tipAmount = state.tipAmount.toDoubleOrNull() ?: 0.0,
            currency = state.currency
        )
        val result = processPaymentUseCase(payment)
        result.fold(
            onSuccess = { approved ->
                reduce { state.copy(isProcessing = false, approvedPayment = payment) }
                postSideEffect(AuthorizationSideEffect.NavigateToConfirmation)
            },
            onFailure = { error ->
                val message = error.message ?: "An unexpected error occurred."
                reduce { state.copy(isProcessing = false, errorMessage = message) }
                postSideEffect(AuthorizationSideEffect.ShowError(message))
            }
        )
    }

    private fun onDone() = intent {
        postSideEffect(AuthorizationSideEffect.NavigateBack)
    }
}

