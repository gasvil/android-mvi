package com.example.authorization.screens.paymententry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.authorization.ui.AuthorizationIntent
import com.example.authorization.ui.AuthorizationState
import com.example.common.currency.Currency
import com.example.common.ui.theme.MVIApplicationTheme

@Composable
fun PaymentEntryContent(
    state: AuthorizationState,
    onIntent: (AuthorizationIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Payment Details",
            style = MaterialTheme.typography.headlineSmall
        )

        // ─── Currency Switcher ────────────────────────────────────────────────
        Text(
            text = "Select Currency",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Currency.entries.forEach { currency ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    RadioButton(
                        selected = state.currency == currency,
                        onClick = { onIntent(AuthorizationIntent.OnCurrencyChanged(currency)) }
                    )
                    Text(
                        text = currency.name,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ─── Amount Input ─────────────────────────────────────────────────────
        TextField(
            value = state.amount,
            onValueChange = { onIntent(AuthorizationIntent.OnAmountChanged(it)) },
            label = { Text("Amount") },
            placeholder = { Text("Enter amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        // ─── Tip Amount Input ─────────────────────────────────────────────────
        TextField(
            value = state.tipAmount,
            onValueChange = { onIntent(AuthorizationIntent.OnTipAmountChanged(it)) },
            label = { Text("Tip Amount") },
            placeholder = { Text("Enter tip amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ─── Submit Button ────────────────────────────────────────────────────
        Button(
            onClick = { onIntent(AuthorizationIntent.OnPaymentEntrySubmitted) },
            enabled = state.amount.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Continue")
        }
    }
}

// ─── Previews ────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "PaymentEntry — Idle")
@Composable
private fun PaymentEntryContentPreview() {
    MVIApplicationTheme {
        PaymentEntryContent(state = AuthorizationState(), onIntent = {})
    }
}

