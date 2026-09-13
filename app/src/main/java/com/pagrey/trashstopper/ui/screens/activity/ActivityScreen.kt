package com.pagrey.trashstopper.ui.screens.activity

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pagrey.trashstopper.data.CallEventEntity
import com.pagrey.trashstopper.data.TrashStopperDataStore
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun ActivityScreen(modifier: Modifier = Modifier) {
    var events by remember { mutableStateOf<List<CallEventEntity>>(emptyList()) }
    val context = LocalContext.current
    val store = remember(context) { TrashStopperDataStore(context) }

    LaunchedEffect(store) {
        events = store.recentCallEvents(50)
    }

    SectionScreen("Actividad", "Tus llamadas recientes y las decisiones de protección.", modifier) {
        if (events.isEmpty()) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = { Text("Sin actividad todavía") },
                    supportingContent = { Text("Las llamadas analizadas aparecerán aquí.") }
                )
            }
        } else {
            events.forEach { event ->
                val riskContainer = when {
                    event.riskScore >= 75 -> MaterialTheme.colorScheme.errorContainer
                    event.riskScore >= 50 -> MaterialTheme.colorScheme.surfaceVariant
                    else -> MaterialTheme.colorScheme.surface
                }
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(containerColor = riskContainer)
                ) {
                    ListItem(
                        headlineContent = { Text(event.phoneNumber) },
                        supportingContent = { Text("${event.result} · Riesgo ${event.riskScore}/100") },
                        trailingContent = {
                            Text(
                                event.action,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
            }
        }
    }
}
