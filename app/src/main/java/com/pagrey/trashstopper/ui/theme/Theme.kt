package com.pagrey.trashstopper.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = TrashPrimaryLight,
    onPrimary = TrashOnPrimaryLight,
    primaryContainer = TrashPrimaryContainerLight,
    onPrimaryContainer = TrashOnPrimaryContainerLight,
    background = TrashBackgroundLight,
    surface = TrashSurfaceLight,
    surfaceVariant = TrashSurfaceVariantLight,
    onSurface = TrashOnSurfaceLight,
    onSurfaceVariant = TrashOnSurfaceVariantLight
)

private val DarkColors = darkColorScheme(
    primary = TrashPrimaryDark,
    onPrimary = TrashOnPrimaryDark,
    primaryContainer = TrashPrimaryContainerDark,
    onPrimaryContainer = TrashOnPrimaryContainerDark,
    background = TrashBackgroundDark,
    surface = TrashSurfaceDark,
    surfaceVariant = TrashSurfaceVariantDark,
    onSurface = TrashOnSurfaceDark,
    onSurfaceVariant = TrashOnSurfaceVariantDark
)

@Composable
fun TrashStopperTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        shapes = TrashShapes,
        content = content
    )
}
