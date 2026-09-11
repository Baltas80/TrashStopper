package com.pagrey.trashstopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pagrey.trashstopper.ui.TrashStopperApp
import com.pagrey.trashstopper.ui.theme.TrashStopperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrashStopperTheme {
                TrashStopperApp()
            }
        }
    }
}
