package com.pagrey.trashstopper.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pagrey.trashstopper.data.NumberEntity
import com.pagrey.trashstopper.data.TrashStopperDataStore
import com.pagrey.trashstopper.reputation.ReputationSnapshotImporter
import com.pagrey.trashstopper.screening.PhoneNumberNormalizer
import com.pagrey.trashstopper.sync.SpainSpamListProvider
import com.pagrey.trashstopper.ui.screens.common.SectionScreen
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    var number by rememberSaveable { mutableStateOf("") }
    var result by remember { mutableStateOf<NumberEntity?>(null) }
    var searched by rememberSaveable { mutableStateOf(false) }
    var syncing by rememberSaveable { mutableStateOf(false) }
    var syncError by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val store = remember(context) { TrashStopperDataStore(context) }

    fun searchLocal() {
        scope.launch {
            val normalized = PhoneNumberNormalizer.normalize(number)
            result = if (normalized.isBlank()) null else store.findNumber(normalized)
            searched = true
        }
    }

    SectionScreen("Buscar número", "Consulta la reputación antes de devolver una llamada.", modifier) {
        OutlinedTextField(
            value = number,
            onValueChange = { number = it; searched = false; syncError = false },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Número de teléfono") },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) }
        )
        Button(
            onClick = { searchLocal() },
            enabled = number.isNotBlank() && !syncing,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Consultar reputación") }

        if (searched) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                val item = result
                if (item == null) {
                    ListItem(
                        headlineContent = { Text("Sin datos locales") },
                        supportingContent = { Text("Puedes actualizar la biblioteca de reputación y volver a consultar.") },
                        trailingContent = {
                            Button(
                                enabled = !syncing,
                                onClick = {
                                    scope.launch {
                                        syncing = true
                                        syncError = false
                                        runCatching {
                                            val payload = SpainSpamListProvider().fetch(context)
                                            ReputationSnapshotImporter(context).import(payload)
                                        }.onSuccess {
                                            val normalized = PhoneNumberNormalizer.normalize(number)
                                            result = if (normalized.isBlank()) null else store.findNumber(normalized)
                                        }.onFailure {
                                            syncError = true
                                        }
                                        syncing = false
                                    }
                                }
                            ) { Text(if (syncing) "Actualizando…" else "Actualizar") }
                        }
                    )
                } else {
                    ListItem(
                        headlineContent = { Text(item.phoneNumber) },
                        supportingContent = {
                            Text("${item.category ?: "Sin categoría"} · Riesgo ${item.riskScore}/100 · ${item.reportCount} reportes")
                        },
                        trailingContent = { Text(if (item.verified) "Verificado" else "No verificado") }
                    )
                }
            }
            if (syncError) {
                Text("No se pudo actualizar la biblioteca. Conservamos los datos locales.")
            }
        }
    }
}
