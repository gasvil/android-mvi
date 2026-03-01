package com.example.activation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.activation.ui.LoginIntent
import com.example.activation.ui.LoginState
import com.example.common.ui.theme.MVIApplicationTheme

@Composable
fun LoginContent(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            } else {
                Button(
                    onClick = { onIntent(LoginIntent.OnActivate) },
                    enabled = state.isLoginButtonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Activar")
                }
            }

            if (state.errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

// ─── Previews ────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Activation — Idle")
@Composable
private fun LoginContentIdlePreview() {
    MVIApplicationTheme { LoginContent(state = LoginState(), onIntent = {}) }
}

@Preview(showBackground = true, name = "Activation — Loading")
@Composable
private fun LoginContentLoadingPreview() {
    MVIApplicationTheme {
        LoginContent(
            state = LoginState(isLoading = true),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "Activation — Error")
@Composable
private fun LoginContentErrorPreview() {
    MVIApplicationTheme {
        LoginContent(
            state = LoginState(
                errorMessage = "Activation failed. Please try again."
            ),
            onIntent = {}
        )
    }
}

