package com.example.reports.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reports.domain.model.Operation
import com.example.reports.domain.model.OperationType
import com.example.common.ui.theme.MVIApplicationTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit

@Composable
fun ReportsContent(
    state: ReportsState,
    onIntent: (ReportsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()

            state.errorMessage != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(onClick = { onIntent(ReportsIntent.RetryClicked) }) {
                        Text("Retry")
                    }
                }
            }

            state.operations.isEmpty() -> {
                Text(
                    text = "No operations found.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Operations",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.operations, key = { it.id }) { operation ->
                            OperationItem(operation = operation)
                        }
                    }
                }
            }
        }
    }
}

// ─── Operation Item ───────────────────────────────────────────────────────────

@Composable
private fun OperationItem(operation: Operation, modifier: Modifier = Modifier) {
    val (indicatorColor, amountPrefix) = when (operation.type) {
        OperationType.CREDIT   -> Color(0xFF2E7D32) to "+"
        OperationType.DEBIT    -> Color(0xFFC62828) to "-"
        OperationType.TRANSFER -> Color(0xFF1565C0) to "↔"
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color = indicatorColor, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = operation.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = operation.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
                Text(
                    text = dateFormatter.format(Date(operation.dateMillis)),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$amountPrefix ${"%.2f".format(operation.amount)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = indicatorColor
            )
        }
    }
}

// ─── Previews ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Reports — Loading")
@Composable
private fun ReportsLoadingPreview() {
    MVIApplicationTheme { ReportsContent(state = ReportsState(isLoading = true), onIntent = {}) }
}

@Preview(showBackground = true, name = "Reports — List")
@Composable
private fun ReportsListPreview() {
    val now = System.currentTimeMillis()
    val sampleOps = listOf(
        Operation(UUID.randomUUID().toString(), "Salary Deposit", "Monthly salary", 3500.0, OperationType.CREDIT, now),
        Operation(UUID.randomUUID().toString(), "Netflix", "Subscription", 15.99, OperationType.DEBIT, now - TimeUnit.DAYS.toMillis(1)),
        Operation(UUID.randomUUID().toString(), "Transfer to John", "Rent split", 600.0, OperationType.TRANSFER, now - TimeUnit.DAYS.toMillis(2))
    )
    MVIApplicationTheme { ReportsContent(state = ReportsState(operations = sampleOps), onIntent = {}) }
}

@Preview(showBackground = true, name = "Reports — Error")
@Composable
private fun ReportsErrorPreview() {
    MVIApplicationTheme { ReportsContent(state = ReportsState(errorMessage = "Network error. Please try again."), onIntent = {}) }
}

