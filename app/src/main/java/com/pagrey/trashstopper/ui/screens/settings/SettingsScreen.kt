package com.pagrey.trashstopper.ui.screens.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    var darkMode by rememberSaveable { mutableStateOf(false) }
    SectionScreen("Ajustes", "Privacidad, apariencia y configuración de la aplicación.", modifier) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Modo oscuro") },
                supportingContent = { Text("Usar una interfaz adaptada a poca luz") },
                trailingContent = { Switch(darkMode, { darkMode = it }) }
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(headlineContent = { Text("Privacidad") }, supportingContent = { Text("Datos locales, permisos y consentimiento") })
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(headlineContent = { Text("Base de reputación") }, supportingContent = { Text("Última actualización: disponible al conectar") })
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(headlineContent = { Text("Plan") }, supportingContent = { Text("Gratis · Cambiar a Premium") })
        }
    }
}
