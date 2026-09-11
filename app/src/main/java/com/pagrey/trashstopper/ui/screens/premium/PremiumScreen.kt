package com.pagrey.trashstopper.ui.screens.premium

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun PremiumScreen(modifier: Modifier = Modifier) {
    SectionScreen("Premium", "Más protección, menos interrupciones.", modifier) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Protección avanzada") },
                supportingContent = { Text("Bloqueo automático de spam, fraude y campañas.") }
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Reputación prioritaria") },
                supportingContent = { Text("Actualizaciones más frecuentes de la base de reputación.") }
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Sin publicidad") },
                supportingContent = { Text("La experiencia Premium no muestra anuncios.") }
            )
        }
        Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
            Text("Continuar con Premium · 24,99 €/año")
        }
    }
}
