package com.pagrey.trashstopper.ui.screens.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun SettingsScreen(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionScreen("Ajustes", "Privacidad, apariencia y configuración de la aplicación.", modifier) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Modo oscuro") },
                supportingContent = { Text("Usar una interfaz adaptada a poca luz") },
                trailingContent = { Switch(checked = darkTheme, onCheckedChange = onDarkThemeChange) }
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Privacidad") },
                supportingContent = { Text("Datos locales, permisos y consentimiento") }
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Base de reputación") },
                supportingContent = { Text("La protección local continúa sin conexión") }
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Plan actual") },
                supportingContent = { Text("Gratis · Protección esencial") }
            )
        }
    }
}
