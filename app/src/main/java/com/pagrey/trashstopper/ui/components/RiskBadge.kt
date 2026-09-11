package com.pagrey.trashstopper.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.Color

@Composable
fun RiskBadge(
    label: String,
    risk: RiskLevel,
    modifier: Modifier = Modifier
) {
    val icon = when (risk) {
        RiskLevel.SAFE -> Icons.Default.CheckCircle
        RiskLevel.CAUTION -> Icons.Default.Warning
        RiskLevel.HIGH -> Icons.Default.ReportProblem
        RiskLevel.CRITICAL -> Icons.Default.Error
    }
    AssistChip(
        onClick = {},
        modifier = modifier,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = label) }
    )
}

enum class RiskLevel { SAFE, CAUTION, HIGH, CRITICAL }
