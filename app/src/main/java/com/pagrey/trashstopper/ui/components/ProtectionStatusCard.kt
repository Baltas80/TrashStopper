package com.pagrey.trashstopper.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProtectionStatusCard(
    active: Boolean,
    lastUpdate: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (active) "Protección activa" else "Protección requiere atención",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = if (active) "Trash Stopper está vigilando tus llamadas." else "Revisa la configuración para recuperar la protección.",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Base de reputación: $lastUpdate", style = MaterialTheme.typography.labelMedium)
                TextButton(onClick = onAction) {
                    Text(if (active) "Configurar" else "Reparar")
                }
            }
        }
    }
}
