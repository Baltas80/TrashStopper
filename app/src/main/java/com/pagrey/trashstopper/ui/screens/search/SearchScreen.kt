package com.pagrey.trashstopper.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pagrey.trashstopper.ui.screens.common.SectionScreen

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    var number by rememberSaveable { mutableStateOf("") }
    SectionScreen("Buscar número", "Consulta la reputación antes de devolver una llamada.", modifier) {
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Número de teléfono") },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) }
        )
        Button(
            onClick = { },
            enabled = number.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Consultar reputación") }
    }
}
