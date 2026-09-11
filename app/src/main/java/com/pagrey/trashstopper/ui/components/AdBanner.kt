package com.pagrey.trashstopper.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Presentation-only ad slot. Eligibility is supplied by the monetization layer. */
@Composable
fun AdBanner(
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    if (!visible) return
    Card(modifier = modifier.fillMaxWidth().heightIn(min = 50.dp)) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Publicidad")
        }
    }
}
