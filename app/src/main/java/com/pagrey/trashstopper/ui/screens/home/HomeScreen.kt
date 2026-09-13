package com.pagrey.trashstopper.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pagrey.trashstopper.ui.components.AdBanner
import com.pagrey.trashstopper.ui.components.ProtectionStatusCard

@Composable
fun HomeScreen(
    adsEnabled: Boolean,
    protectionActive: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Trash Stopper", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Identifica. Advierte. Bloquea.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        ProtectionStatusCard(
            active = protectionActive,
            lastUpdate = "Actualizada recientemente",
            onAction = {}
        )
        Text("Protección rápida", style = MaterialTheme.typography.titleLarge)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Defensa activa", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Spam, fraude, llamadas automatizadas y números desconocidos se evalúan con reglas locales.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("● Base local", style = MaterialTheme.typography.labelMedium)
                    Text("● Sincronización", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
        AdBanner(visible = adsEnabled)
    }
}
