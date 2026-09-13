package com.pagrey.trashstopper.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pagrey.trashstopper.ui.theme.TrashSurfaceElevatedDark

@Composable
fun ProtectionStatusCard(
    active: Boolean,
    lastUpdate: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (active) TrashSurfaceElevatedDark else MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
