package com.pagrey.trashstopper.ui.screens.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.pagrey.trashstopper.data.TrashStopperDataStore
import com.pagrey.trashstopper.screening.PhoneNumberNormalizer
import com.pagrey.trashstopper.ui.screens.common.SectionScreen
import kotlinx.coroutines.launch

@Composable
fun ReportsScreen(modifier: Modifier = Modifier) {
    var number by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("SPAM") }
    var note by rememberSaveable { mutableStateOf("") }
    var submitted by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val store = remember(context) { TrashStopperDataStore(context) }

    SectionScreen(
        title = "Reportar número",
        subtitle = "Añade números sospechosos a la base local de Trash Stopper.",
        modifier = modifier
    ) {
        OutlinedTextField(
            value = number,
            onValueChange = { number = it; submitted = false },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Número de teléfono") }
        )

        Text("Tipo de llamada")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            listOf("SPAM", "FRAUDE").forEach { option ->
                FilterChip(
                    selected = category == option,
                    onClick = { category = option },
                    label = { Text(option) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            listOf("ROBOCALL", "TELEMARKETING").forEach { option ->
                FilterChip(
                    selected = category == option,
                    onClick = { category = option },
                    label = { Text(option) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            label = { Text("Comentario (opcional) ") },
            placeholder = { Text("Qué ocurrió con esta llamada") }
        )

        Button(
            onClick = {
                scope.launch {
                    submitted = store.submitReport(number, category, note)
                    if (submitted) {
                        number = PhoneNumberNormalizer.normalize(number)
                        note = ""
                    }
                }
            },
            enabled = PhoneNumberNormalizer.normalize(number).isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir a la base de datos")
        }

        if (submitted) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = { Text("Número añadido") },
                    supportingContent = { Text("El reporte se ha guardado en la base local y ya forma parte de su reputación.") }
                )
            }
        }
    }
}
