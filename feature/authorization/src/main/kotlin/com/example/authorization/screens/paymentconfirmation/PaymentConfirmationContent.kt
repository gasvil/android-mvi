package com.example.authorization.screens.paymentconfirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.authorization.ui.AuthorizationIntent
import com.example.authorization.ui.AuthorizationState
import com.example.common.currency.Currency
import com.example.common.ui.theme.MVIApplicationTheme

@Composable
fun PaymentConfirmationContent(
    state: AuthorizationState,
    onIntent: (AuthorizationIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Payment Approved!",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        // ─── Payment Summary ──────────────────────────────────────────────────
        SummaryRow(label = "Currency", value = state.currency.name)
        SummaryRow(label = "Amount", value = state.amount.ifBlank { "0.00" })
        SummaryRow(label = "Tip", value = state.tipAmount.ifBlank { "0.00" })

        val total = (state.amount.toDoubleOrNull() ?: 0.0) +
                (state.tipAmount.toDoubleOrNull() ?: 0.0)
        SummaryRow(label = "Total", value = String.format("%.2f", total))

        HorizontalDivider()

        if (state.approvedPayment != null) {
            Text(
                text = "Transaction ID: ${state.approvedPayment.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onIntent(AuthorizationIntent.OnDone) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Done")
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

