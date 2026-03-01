package com.example.activation.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.activation.ui.LoginSideEffect
import com.example.activation.ui.LoginViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.NavigateToHome -> onNavigateToHome()
            is LoginSideEffect.ShowToastError ->
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_LONG).show()
        }
    }

    LoginContent(
        state = state,
        onIntent = viewModel::handleIntent,
        modifier = modifier
    )
}

