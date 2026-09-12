package com.pagrey.trashstopper

import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.pagrey.trashstopper.ui.TrashStopperApp
import com.pagrey.trashstopper.ui.theme.TrashStopperTheme

class MainActivity : ComponentActivity() {
    private val roleRequestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // The platform owns the role decision. No restricted call-log permission is needed.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by rememberSaveable { mutableStateOf(false) }
            TrashStopperTheme(darkTheme = darkTheme) {
                TrashStopperApp(
                    darkTheme = darkTheme,
                    onDarkThemeChange = { darkTheme = it }
                )
            }
        }

        requestCallScreeningRoleIfNeeded()
    }

    private fun requestCallScreeningRoleIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return

        val roleManager = getSystemService(RoleManager::class.java) ?: return
        if (!roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)) return
        if (roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) return

        val intent: Intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
        roleRequestLauncher.launch(intent)
    }
}
