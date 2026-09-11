package com.pagrey.trashstopper.ui.screens.family

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun FamilyScreen(modifier: Modifier = Modifier) {
    SectionScreen("Family", "Protección coordinada para varios dispositivos.", modifier) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Hasta varios dispositivos") },
                supportingContent = { Text("Gestiona la protección familiar desde una cuenta.") }
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Privacidad por diseño") },
                supportingContent = { Text("Sin vigilancia invasiva del contenido de las llamadas.") }
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Sin publicidad") },
                supportingContent = { Text("Todos los dispositivos del plan Family funcionan sin anuncios.") }
            )
        }
        Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
            Text("Continuar con Family · 39,99 €/año")
        }
    }
}
