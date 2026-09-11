package com.pagrey.trashstopper.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Trash Stopper", style = MaterialTheme.typography.headlineMedium)
        Text("Identifica. Advierte. Bloquea.", style = MaterialTheme.typography.bodyLarge)
        ProtectionStatusCard(
            active = protectionActive,
            lastUpdate = "Actualizada recientemente",
            onAction = {}
        )
        Text("Protección rápida", style = MaterialTheme.typography.titleLarge)
        Text("Bloqueo de spam y fraude, identificación de llamadas y consulta de números.")
        AdBanner(visible = adsEnabled)
    }
}
