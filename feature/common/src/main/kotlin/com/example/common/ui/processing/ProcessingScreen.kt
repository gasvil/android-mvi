package com.example.common.ui.processing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun ProcessingScreen(
    title: String,
    subtitle: String,
    onStarted: () -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null
) {
    LaunchedEffect(Unit) {
        onStarted()
    }

    ProcessingContent(
        title = title,
        subtitle = subtitle,
        modifier = modifier,
        errorMessage = errorMessage
    )
}

