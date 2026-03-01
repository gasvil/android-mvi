package com.example.voids.ui

import androidx.lifecycle.ViewModel
import com.example.common.transaction.Transaction
import com.example.voids.usecase.VoidTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class VoidViewModel @Inject constructor(
    private val voidTransactionUseCase: VoidTransactionUseCase
) : ViewModel(), ContainerHost<VoidState, VoidSideEffect> {

    override val container = container<VoidState, VoidSideEffect>(VoidState())

    fun handleIntent(intent: VoidIntent) {
        when (intent) {
            is VoidIntent.OnSearchQueryChanged -> onSearchQueryChanged(intent.query)
            is VoidIntent.OnTransactionSelected -> onTransactionSelected(intent.transaction)
            is VoidIntent.OnVoidConfirmed -> onVoidConfirmed()
            is VoidIntent.OnProcessingCompleted -> onProcessingCompleted()
            is VoidIntent.OnDone -> onDone()
        }
    }

    private fun onSearchQueryChanged(query: String) = intent {
        reduce { state.copy(searchQuery = query) }
    }

    private fun onTransactionSelected(transaction: Transaction) = intent {
        reduce { state.copy(selectedTransaction = transaction) }
        postSideEffect(VoidSideEffect.NavigateToProcessing)
    }

    private fun onVoidConfirmed() = intent {
        postSideEffect(VoidSideEffect.NavigateToProcessing)
    }

    private fun onProcessingCompleted() = intent {
        val transaction = state.selectedTransaction ?: return@intent
        reduce { state.copy(isProcessing = true, errorMessage = null) }
        val result = voidTransactionUseCase(transaction)
        result.fold(
            onSuccess = { voided ->
                reduce { state.copy(isProcessing = false, voidedTransaction = voided) }
                postSideEffect(VoidSideEffect.NavigateToConfirmation)
            },
            onFailure = { error ->
                val message = error.message ?: "An unexpected error occurred."
                reduce { state.copy(isProcessing = false, errorMessage = message) }
                postSideEffect(VoidSideEffect.ShowError(message))
            }
        )
    }

    private fun onDone() = intent {
        postSideEffect(VoidSideEffect.NavigateBack)
    }
}



