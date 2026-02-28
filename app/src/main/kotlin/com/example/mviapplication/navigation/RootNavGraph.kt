package com.example.mviapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.activation.ui.LoginScreen

@Composable
fun RootNavGraph() {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = ROUTE_LOGIN
    ) {
        composable(route = ROUTE_LOGIN) {
            LoginScreen(
                onNavigateToHome = {
                    rootNavController.navigate(ROUTE_MAIN) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(route = ROUTE_MAIN) {
            MainScaffold()
        }
    }
}

