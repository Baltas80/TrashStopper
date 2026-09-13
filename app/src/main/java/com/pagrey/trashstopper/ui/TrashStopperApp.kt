package com.pagrey.trashstopper.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pagrey.trashstopper.ui.screens.activity.ActivityScreen
import com.pagrey.trashstopper.ui.screens.home.HomeScreen
import com.pagrey.trashstopper.ui.screens.protection.ProtectionScreen
import com.pagrey.trashstopper.ui.screens.reports.ReportsScreen
import com.pagrey.trashstopper.ui.screens.search.SearchScreen
import com.pagrey.trashstopper.ui.screens.settings.SettingsScreen

enum class TopLevelDestination(val label: String) {
    HOME("Inicio"), ACTIVITY("Actividad"), SEARCH("Buscar"), REPORTS("Reportar"), PROTECTION("Protección"), SETTINGS("Ajustes")
}

@Composable
fun TrashStopperApp(
    darkTheme: Boolean = true,
    onDarkThemeChange: (Boolean) -> Unit = {}
) {
    var destination by rememberSaveable { mutableStateOf(TopLevelDestination.HOME) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                TopLevelDestination.entries.forEach { item ->
                    val icon = when (item) {
                        TopLevelDestination.HOME -> Icons.Outlined.Home
                        TopLevelDestination.ACTIVITY -> Icons.Outlined.Call
                        TopLevelDestination.SEARCH -> Icons.Outlined.Search
                        TopLevelDestination.REPORTS -> Icons.Outlined.Flag
                        TopLevelDestination.PROTECTION -> Icons.Outlined.Security
                        TopLevelDestination.SETTINGS -> Icons.Outlined.Settings
                    }
                    NavigationBarItem(
                        selected = destination == item,
                        onClick = { destination = item },
                        icon = { Icon(icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->
        val contentModifier = Modifier.padding(padding)
        when (destination) {
            TopLevelDestination.HOME -> HomeScreen(adsEnabled = true, modifier = contentModifier)
            TopLevelDestination.ACTIVITY -> ActivityScreen(modifier = contentModifier)
            TopLevelDestination.SEARCH -> SearchScreen(modifier = contentModifier)
            TopLevelDestination.REPORTS -> ReportsScreen(modifier = contentModifier)
            TopLevelDestination.PROTECTION -> ProtectionScreen(modifier = contentModifier)
            TopLevelDestination.SETTINGS -> SettingsScreen(
                darkTheme = darkTheme,
                onDarkThemeChange = onDarkThemeChange,
                modifier = contentModifier
            )
        }
    }
}
