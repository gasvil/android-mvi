package com.example.authorization.screens.readcard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authorization.ui.AuthorizationSideEffect
import com.example.authorization.ui.AuthorizationViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ReadCardScreen(
    onNavigateToSignature: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AuthorizationSideEffect.NavigateToSignature -> onNavigateToSignature()
            else -> Unit
        }
    }

    ReadCardContent(
        state = state,
        onIntent = viewModel::handleIntent,
        modifier = modifier
    )
}

