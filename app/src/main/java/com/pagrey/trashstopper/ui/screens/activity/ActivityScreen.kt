package com.pagrey.trashstopper.ui.screens.activity

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pagrey.trashstopper.data.CallEventEntity
import com.pagrey.trashstopper.data.TrashStopperDataStore
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun ActivityScreen(modifier: Modifier = Modifier) {
    var events by remember { mutableStateOf<List<CallEventEntity>>(emptyList()) }
    val store = remember { TrashStopperDataStore(LocalContext.current) }

    LaunchedEffect(Unit) {
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
            for (event in events) {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text(event.phoneNumber) },
                        supportingContent = { Text("${event.result} · Riesgo ${event.riskScore}/100") },
                        trailingContent = { Text(event.action) }
                    )
                }
            }
        }
    }
}
