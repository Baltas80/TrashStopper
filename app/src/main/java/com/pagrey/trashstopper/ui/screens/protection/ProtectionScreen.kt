package com.pagrey.trashstopper.ui.screens.protection

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pagrey.trashstopper.data.ProtectionPreferences
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun ProtectionScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val preferences = remember(context) { ProtectionPreferences(context) }
    var automatic by remember { mutableStateOf(preferences.automaticProtection) }
    var spam by remember { mutableStateOf(preferences.spamBlocking) }
    var fraud by remember { mutableStateOf(preferences.fraudBlocking) }
    var unknown by remember { mutableStateOf(preferences.unknownBlocking) }

    SectionScreen("Protección", "Configura cómo debe actuar Trash Stopper.", modifier) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Protección automática") },
                supportingContent = { Text("Las decisiones se toman primero de forma local.") },
                trailingContent = {
                    Switch(
                        checked = automatic,
                        onCheckedChange = {
                            automatic = it
                            preferences.automaticProtection = it
                        }
                    )
                }
            )
        }
        FilterRow("Bloquear spam", spam) {
            spam = it
            preferences.spamBlocking = it
        }
        FilterRow("Bloquear fraude", fraud) {
            fraud = it
            preferences.fraudBlocking = it
        }
        FilterRow("Bloquear desconocidos", unknown) {
            unknown = it
            preferences.unknownBlocking = it
        }
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
