package com.example.mviapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RemoveCircle
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

    data object Void : BottomNavDestination(
        route = "void",
        label = "Void",
        icon = Icons.Filled.RemoveCircle
    )

    companion object {
        val items: List<BottomNavDestination> = listOf(Home, Void)
    }
}

