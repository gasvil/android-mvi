package com.example.activation.ui

import androidx.lifecycle.ViewModel
import com.example.activation.domain.usecase.ActivateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val activateUseCase: ActivateUseCase,
) : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.OnActivate -> onActivate()
        }
    }

    private fun onActivate() = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }
        val result = activateUseCase()
        result.fold(
            onSuccess = { user ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(LoginSideEffect.NavigateToHome)
            },
            onFailure = { error ->
                val message = error.message ?: "An unexpected error occurred."
                reduce { state.copy(isLoading = false, errorMessage = message) }
                postSideEffect(LoginSideEffect.ShowToastError(message))
            }
        )
    }
}

