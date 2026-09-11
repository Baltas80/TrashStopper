package com.pagrey.trashstopper.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.pagrey.trashstopper.screening.PhoneNumberNormalizer
import com.pagrey.trashstopper.ui.screens.common.SectionScreen
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    var number by rememberSaveable { mutableStateOf("") }
    var result by remember { mutableStateOf<NumberEntity?>(null) }
    var searched by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val store = remember { TrashStopperDataStore(LocalContext.current) }

    SectionScreen("Buscar número", "Consulta la reputación antes de devolver una llamada.", modifier) {
        OutlinedTextField(
            value = number,
            onValueChange = { number = it; searched = false },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Número de teléfono") },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) }
        )
        Button(
            onClick = {
                scope.launch {
                    val normalized = PhoneNumberNormalizer.normalize(number)
                    result = if (normalized.isBlank()) null else store.findNumber(normalized)
                    searched = true
                }
            },
            enabled = number.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Consultar reputación") }

        if (searched) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                val item = result
                if (item == null) {
                    ListItem(
                        headlineContent = { Text("Sin datos locales") },
                        supportingContent = { Text("Todavía no tenemos una reputación registrada para este número.") }
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
        }
    }
}
