package com.example.authorization.screens.signature

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.authorization.ui.AuthorizationIntent
import com.example.authorization.ui.AuthorizationState
import com.example.common.ui.theme.MVIApplicationTheme

@Composable
fun SignatureContent(
    state: AuthorizationState,
    onIntent: (AuthorizationIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val paths = remember { mutableStateListOf<List<Offset>>() }
    val currentPath = remember { mutableStateListOf<Offset>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Signature",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Please sign in the box below.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // ─── Signature Canvas ─────────────────────────────────────────────────
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPath.clear()
                            currentPath.add(offset)
                        },
                        onDrag = { change, _ ->
                            currentPath.add(change.position)
                        },
                        onDragEnd = {
                            paths.add(currentPath.toList())
                            currentPath.clear()
                        }
                    )
                }
        ) {
            // Draw completed paths
            paths.forEach { pathPoints ->
                if (pathPoints.size > 1) {
                    val path = Path().apply {
                        moveTo(pathPoints.first().x, pathPoints.first().y)
                        pathPoints.drop(1).forEach { lineTo(it.x, it.y) }
                    }
                    drawPath(path, color = Color.Black, style = Stroke(width = 4f))
                }
            }
            // Draw current path
            if (currentPath.size > 1) {
                val path = Path().apply {
                    moveTo(currentPath.first().x, currentPath.first().y)
                    currentPath.drop(1).forEach { lineTo(it.x, it.y) }
                }
                drawPath(path, color = Color.Black, style = Stroke(width = 4f))
            }
        }

        TextButton(
            onClick = { paths.clear(); currentPath.clear() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Signature")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onIntent(AuthorizationIntent.OnSignatureConfirmed) },
            enabled = paths.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Confirm Signature")
        }
    }
}

// ─── Previews ────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Signature — Idle")
@Composable
private fun SignatureContentPreview() {
    MVIApplicationTheme {
        SignatureContent(state = AuthorizationState(), onIntent = {})
    }
}

