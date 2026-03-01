package com.example.voids.screens.voidconfirmation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.example.voids.ui.VoidSideEffect
import com.example.voids.ui.VoidViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun VoidConfirmationScreen(
    onNavigateBack: () -> Unit,
    graphBackStackEntry: NavBackStackEntry,
    modifier: Modifier = Modifier,
    viewModel: VoidViewModel = hiltViewModel(graphBackStackEntry)
) {
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is VoidSideEffect.NavigateBack -> onNavigateBack()
            else -> Unit
        }
    }

    VoidConfirmationContent(
        state = state,
        onIntent = viewModel::handleIntent,
        modifier = modifier
    )
}
