package com.pagrey.trashstopper.ui.screens.activity

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun ActivityScreen(modifier: Modifier = Modifier) {
    SectionScreen("Actividad", "Tus llamadas recientes y las decisiones de protección.", modifier) {
        ElevatedCard {
            ListItem(
                headlineContent = { Text("+34 900 123 456") },
                supportingContent = { Text("Telemarketing · Riesgo alto") },
                trailingContent = { Text("Bloqueada") }
            )
        }
        ElevatedCard {
            ListItem(
                headlineContent = { Text("+34 611 222 333") },
                supportingContent = { Text("Número desconocido · Riesgo moderado") },
                trailingContent = { Text("Silenciada") }
            )
        }
    }
}
