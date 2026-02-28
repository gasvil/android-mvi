package com.example.reports.ui

import androidx.lifecycle.ViewModel
import com.example.reports.domain.usecase.GetOperationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getOperationsUseCase: GetOperationsUseCase
) : ViewModel(), ContainerHost<ReportsState, ReportsSideEffect> {

    override val container = container<ReportsState, ReportsSideEffect>(ReportsState()) {
        handleIntent(ReportsIntent.LoadOperations)
    }

    fun handleIntent(intent: ReportsIntent) {
        when (intent) {
            is ReportsIntent.LoadOperations -> loadOperations()
            is ReportsIntent.RetryClicked -> loadOperations()
        }
    }

    private fun loadOperations() = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }
        val result = getOperationsUseCase()
        result.fold(
            onSuccess = { operations ->
                reduce { state.copy(isLoading = false, operations = operations) }
            },
            onFailure = { error ->
                val message = error.message ?: "An unexpected error occurred."
                reduce { state.copy(isLoading = false, errorMessage = message) }
                postSideEffect(ReportsSideEffect.ShowToastError(message))
            }
        )
    }
}

