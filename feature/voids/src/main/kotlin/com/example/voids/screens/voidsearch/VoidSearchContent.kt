package com.example.voids.screens.voidsearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

// Stub transactions for demo purposes
private val stubTransactions = listOf(
    Transaction(amount = 100.00, tipAmount = 10.00, currency = Currency.PEN),
    Transaction(amount = 250.50, tipAmount = 0.00, currency = Currency.USD),
    Transaction(amount = 75.00, tipAmount = 5.00, currency = Currency.PEN)
)

@Composable
fun VoidSearchContent(
    state: VoidState,
    onIntent: (VoidIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val filtered = stubTransactions.filter {
        state.searchQuery.isBlank() ||
            it.amount.toString().contains(state.searchQuery) ||
            it.currency.name.contains(state.searchQuery, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Void Transaction",
            style = MaterialTheme.typography.headlineSmall
        )

        // ─── Search Field ─────────────────────────────────────────────────────
        TextField(
            value = state.searchQuery,
            onValueChange = { onIntent(VoidIntent.OnSearchQueryChanged(it)) },
            label = { Text("Search transactions") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        HorizontalDivider()

        // ─── Transaction List ─────────────────────────────────────────────────
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtered) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = { onIntent(VoidIntent.OnTransactionSelected(transaction)) }
                )
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${transaction.currency.name} ${transaction.amount}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tip: ${transaction.tipAmount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "Void →",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// ─── Previews ────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "VoidSearch — Idle")
@Composable
private fun VoidSearchContentPreview() {
    MVIApplicationTheme {
        VoidSearchContent(state = VoidState(), onIntent = {})
    }
}
