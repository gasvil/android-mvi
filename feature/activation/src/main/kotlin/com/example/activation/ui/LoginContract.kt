package com.example.activation.ui

// ─── State ───────────────────────────────────────────────────────────────────
data class LoginState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isLoginButtonEnabled: Boolean
        get() = true
}

// ─── Intents ─────────────────────────────────────────────────────────────────
sealed class LoginIntent {
    data object OnActivate : LoginIntent()
}

// ─── Side Effects ─────────────────────────────────────────────────────────────
sealed class LoginSideEffect {
    data object NavigateToHome : LoginSideEffect()
    data class ShowToastError(val message: String) : LoginSideEffect()
}

