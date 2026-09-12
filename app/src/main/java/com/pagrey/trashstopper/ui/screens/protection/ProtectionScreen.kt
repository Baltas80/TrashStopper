package com.pagrey.trashstopper.ui.screens.protection

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pagrey.trashstopper.data.ProtectionPreferences
import com.pagrey.trashstopper.data.RuleEntity
import com.pagrey.trashstopper.data.TrashStopperDataStore
import com.pagrey.trashstopper.screening.PhoneNumberNormalizer
import com.pagrey.trashstopper.ui.screens.common.SectionScreen
import kotlinx.coroutines.launch

@Composable
fun ProtectionScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val preferences = remember(context) { ProtectionPreferences(context) }
    val store = remember(context) { TrashStopperDataStore(context) }
    val scope = rememberCoroutineScope()
    var automatic by remember { mutableStateOf(preferences.automaticProtection) }
    var spam by remember { mutableStateOf(preferences.spamBlocking) }
    var fraud by remember { mutableStateOf(preferences.fraudBlocking) }
    var unknown by remember { mutableStateOf(preferences.unknownBlocking) }
    var trustRules by remember { mutableStateOf<List<RuleEntity>>(emptyList()) }
    var showTrustDialog by remember { mutableStateOf(false) }
    var newNumber by remember { mutableStateOf("") }

    LaunchedEffect(store) {
        trustRules = store.getAllRules().filter { it.action.equals("ALLOW", ignoreCase = true) }
    }

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

        OutlinedButton(
            onClick = { showTrustDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar lista de confianza")
        }

        trustRules.forEach { rule ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = { Text(rule.phoneNumber) },
                    supportingContent = { Text("Número siempre permitido") },
                    trailingContent = {
                        TextButton(onClick = {
                            scope.launch {
                                store.deleteRule(rule.phoneNumber)
                                trustRules = store.getAllRules().filter {
                                    it.action.equals("ALLOW", ignoreCase = true)
                                }
                            }
                        }) {
                            Text("Quitar")
                        }
                    }
                )
            }
        }
    }

    if (showTrustDialog) {
        AlertDialog(
            onDismissRequest = { showTrustDialog = false },
            title = { Text("Añadir número de confianza") },
            text = {
                OutlinedTextField(
                    value = newNumber,
                    onValueChange = { newNumber = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Número de teléfono") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val normalized = PhoneNumberNormalizer.normalize(newNumber)
                    if (normalized.isNotBlank()) {
                        scope.launch {
                            store.saveRule(RuleEntity(phoneNumber = normalized, action = "ALLOW"))
                            trustRules = store.getAllRules().filter {
                                it.action.equals("ALLOW", ignoreCase = true)
                            }
                            newNumber = ""
                            showTrustDialog = false
                        }
                    }
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    newNumber = ""
                    showTrustDialog = false
                }) {
                    Text("Cancelar")
                }
            }
        )
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
