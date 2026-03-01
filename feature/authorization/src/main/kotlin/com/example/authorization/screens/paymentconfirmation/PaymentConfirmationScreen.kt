package com.example.authorization.screens.paymentconfirmation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authorization.ui.AuthorizationSideEffect
import com.example.authorization.ui.AuthorizationViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun PaymentConfirmationScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AuthorizationSideEffect.NavigateBack -> onNavigateBack()
            else -> Unit
        }
    }

    PaymentConfirmationContent(
        state = state,
        onIntent = viewModel::handleIntent,
        modifier = modifier
    )
}

