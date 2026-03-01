package com.example.voids.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.common.ui.processing.ProcessingScreen
import com.example.voids.screens.voidconfirmation.VoidConfirmationScreen
import com.example.voids.screens.voidsearch.VoidSearchScreen
import com.example.voids.ui.VoidIntent
import com.example.voids.ui.VoidSideEffect
import com.example.voids.ui.VoidViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val VOID_GRAPH_ROUTE = "void_graph"

private const val ROUTE_VOID_SEARCH       = "void_search"
private const val ROUTE_VOID_PROCESSING   = "void_processing"
private const val ROUTE_VOID_CONFIRMATION = "void_confirmation"

fun NavGraphBuilder.voidNavGraph(
    navController: NavHostController,
    onVoidFinished: () -> Unit = {}
) {
    navigation(
        route = VOID_GRAPH_ROUTE,
        startDestination = ROUTE_VOID_SEARCH
    ) {
        composable(ROUTE_VOID_SEARCH) { backStackEntry ->
            val graphEntry = remember(backStackEntry) {
                navController.getBackStackEntry(VOID_GRAPH_ROUTE)
            }
            VoidSearchScreen(
                onNavigateToProcessing = {
                    navController.navigate(ROUTE_VOID_PROCESSING)
                },
                graphBackStackEntry = graphEntry
            )
        }

        composable(ROUTE_VOID_PROCESSING) { backStackEntry ->
            val graphEntry = remember(backStackEntry) {
                navController.getBackStackEntry(VOID_GRAPH_ROUTE)
            }
            val viewModel = hiltViewModel<VoidViewModel>(graphEntry)
            val state by viewModel.collectAsState()
            viewModel.collectSideEffect { sideEffect ->
                when (sideEffect) {
                    is VoidSideEffect.NavigateToConfirmation -> {
                        navController.navigate(ROUTE_VOID_CONFIRMATION) {
                            popUpTo(ROUTE_VOID_SEARCH) { inclusive = false }
                        }
                    }
                    else -> Unit
                }
            }
            ProcessingScreen(
                title = "Processing Void",
                subtitle = "Please wait while we void the transaction…",
                onStarted = { viewModel.handleIntent(VoidIntent.OnProcessingCompleted) },
                errorMessage = state.errorMessage
            )
        }

        composable(ROUTE_VOID_CONFIRMATION) { backStackEntry ->
            val graphEntry = remember(backStackEntry) {
                navController.getBackStackEntry(VOID_GRAPH_ROUTE)
            }
            VoidConfirmationScreen(
                onNavigateBack = {
                    navController.navigate(ROUTE_VOID_SEARCH) {
                        popUpTo(VOID_GRAPH_ROUTE) { inclusive = true }
                    }
                    onVoidFinished()
                },
                graphBackStackEntry = graphEntry
            )
        }
    }
}
