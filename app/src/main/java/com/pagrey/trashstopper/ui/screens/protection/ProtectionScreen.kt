package com.pagrey.trashstopper.ui.screens.protection

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun ProtectionScreen(modifier: Modifier = Modifier) {
    var spam by rememberSaveable { mutableStateOf(true) }
    var fraud by rememberSaveable { mutableStateOf(true) }
    var unknown by rememberSaveable { mutableStateOf(false) }
    SectionScreen("Protección", "Configura cómo debe actuar Trash Stopper.", modifier) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Protección automática") },
                supportingContent = { Text("Las decisiones se toman primero de forma local.") },
                trailingContent = { Switch(checked = true, onCheckedChange = null) }
            )
        }
        FilterRow("Bloquear spam", spam) { spam = it }
        FilterRow("Bloquear fraude", fraud) { fraud = it }
        FilterRow("Bloquear desconocidos", unknown) { unknown = it }
        OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) {
            Text("Gestionar lista de confianza")
        }
    }
}

@Composable
private fun FilterRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        ListItem(
            headlineContent = { Text(label) },
            trailingContent = { Switch(checked = checked, onCheckedChange = onCheckedChange) }
        )
    }
}
