package com.example.voids.screens.voidconfirmation

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
import com.example.common.currency.Currency
import com.example.common.transaction.Transaction
import com.example.common.ui.theme.MVIApplicationTheme
import com.example.voids.ui.VoidIntent
import com.example.voids.ui.VoidState

@Composable
fun VoidConfirmationContent(
    state: VoidState,
    onIntent: (VoidIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val transaction = state.voidedTransaction

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
            text = "Void Successful!",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        // ─── Summary ──────────────────────────────────────────────────────────
        SummaryRow(label = "Currency", value = transaction?.currency?.name ?: "-")
        SummaryRow(label = "Amount",   value = transaction?.amount?.toString() ?: "-")
        SummaryRow(label = "Tip",      value = transaction?.tipAmount?.toString() ?: "-")

        HorizontalDivider()

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onIntent(VoidIntent.OnDone) },
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

// ─── Previews ────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "VoidConfirmation — Success")
@Composable
private fun VoidConfirmationContentPreview() {
    MVIApplicationTheme {
        VoidConfirmationContent(
            state = VoidState(
                voidedTransaction = Transaction(
                    amount = 100.00,
                    tipAmount = 10.00,
                    currency = Currency.PEN
                )
            ),
            onIntent = {}
        )
    }
}
