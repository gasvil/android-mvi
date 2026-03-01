package com.example.authorization.navigation

import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authorization.screens.paymentconfirmation.PaymentConfirmationScreen
import com.example.authorization.screens.paymententry.PaymentEntryScreen
import com.example.authorization.screens.readcard.ReadCardScreen
import com.example.authorization.screens.signature.SignatureScreen
import com.example.authorization.ui.AuthorizationIntent
import com.example.authorization.ui.AuthorizationSideEffect
import com.example.authorization.ui.AuthorizationViewModel
import com.example.common.ui.processing.ProcessingScreen
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val AUTHORIZATION_GRAPH_ROUTE = "authorization_graph"

private const val ROUTE_PAYMENT_ENTRY    = "payment_entry"
private const val ROUTE_READ_CARD        = "read_card"
private const val ROUTE_SIGNATURE        = "signature"
private const val ROUTE_PROCESSING       = "processing"
private const val ROUTE_PAYMENT_CONFIRMATION = "payment_confirmation"

fun NavGraphBuilder.authorizationNavGraph(
    navController: NavHostController,
    onAuthorizationFinished: () -> Unit
) {
    navigation(
        route = AUTHORIZATION_GRAPH_ROUTE,
        startDestination = ROUTE_PAYMENT_ENTRY
    ) {
        composable(ROUTE_PAYMENT_ENTRY) {
            PaymentEntryScreen(
                onNavigateToReadCard = {
                    navController.navigate(ROUTE_READ_CARD)
                }
            )
        }

        composable(ROUTE_READ_CARD) {
            ReadCardScreen(
                onNavigateToSignature = {
                    navController.navigate(ROUTE_SIGNATURE)
                }
            )
        }

        composable(ROUTE_SIGNATURE) {
            SignatureScreen(
                onNavigateToProcessing = {
                    navController.navigate(ROUTE_PROCESSING)
                }
            )
        }

        composable(ROUTE_PROCESSING) {
            val viewModel = hiltViewModel<AuthorizationViewModel>()
            val state by viewModel.collectAsState()
            viewModel.collectSideEffect { sideEffect ->
                when (sideEffect) {
                    is AuthorizationSideEffect.NavigateToConfirmation -> {
                        navController.navigate(ROUTE_PAYMENT_CONFIRMATION) {
                            popUpTo(ROUTE_PAYMENT_ENTRY) { inclusive = false }
                        }
                    }
                    else -> Unit
                }
            }
            ProcessingScreen(
                title = "Processing Payment",
                subtitle = "Please wait while we process your transaction…",
                onStarted = { viewModel.handleIntent(AuthorizationIntent.OnProcessingCompleted) },
                errorMessage = state.errorMessage
            )
        }

        composable(ROUTE_PAYMENT_CONFIRMATION) {
            PaymentConfirmationScreen(
                onNavigateBack = {
                    navController.navigate(ROUTE_PAYMENT_ENTRY) {
                        popUpTo(AUTHORIZATION_GRAPH_ROUTE) { inclusive = true }
                    }
                    onAuthorizationFinished()
                }
            )
        }
    }
}

