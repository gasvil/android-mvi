package com.example.mviapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Home : BottomNavDestination(
        route = "home",
        label = "Home",
        icon = Icons.Filled.Home
    )

    data object Reports : BottomNavDestination(
        route = "reports",
        label = "Reports",
        icon = Icons.AutoMirrored.Filled.List
    )

    companion object {
        val items: List<BottomNavDestination> = listOf(Home, Reports)
    }
}

