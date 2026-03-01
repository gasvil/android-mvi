package com.example.mviapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.authorization.navigation.AUTHORIZATION_GRAPH_ROUTE
import com.example.authorization.navigation.authorizationNavGraph
import com.example.voids.navigation.VOID_GRAPH_ROUTE
import com.example.voids.navigation.voidNavGraph

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavDestination.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavDestination.Home.route) {
            val homeNavController = androidx.navigation.compose.rememberNavController()
            NavHost(
                navController = homeNavController,
                startDestination = AUTHORIZATION_GRAPH_ROUTE
            ) {
                authorizationNavGraph(
                    navController = homeNavController,
                    onAuthorizationFinished = {}
                )
            }
        }

        composable(route = BottomNavDestination.Void.route) {
            val voidNavController = androidx.navigation.compose.rememberNavController()
            NavHost(
                navController = voidNavController,
                startDestination = VOID_GRAPH_ROUTE
            ) {
                voidNavGraph(navController = voidNavController)
            }
        }
    }
}


