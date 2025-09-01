// SPDX-License-Identifier: GPL-3.0-only
package org.idumishmi.keyboard.settings.preferences

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import org.idumishmi.keyboard.keyboard.KeyboardSwitcher
import org.idumishmi.keyboard.latin.utils.prefs
import org.idumishmi.keyboard.settings.Setting
import org.idumishmi.keyboard.settings.dialogs.TextInputDialog

@Composable
fun TextInputPreference(setting: Setting, default: String, checkTextValid: (String) -> Boolean = { true }) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val prefs = LocalContext.current.prefs()
    Preference(
        name = setting.title,
        onClick = { showDialog = true },
        description = prefs.getString(setting.key, default)?.takeIf { it.isNotEmpty() }
    )
    if (showDialog) {
        TextInputDialog(
            onDismissRequest = { showDialog = false },
            onConfirmed = {
                prefs.edit().putString(setting.key, it).apply()
                KeyboardSwitcher.getInstance().setThemeNeedsReload()
            },
            initialText = prefs.getString(setting.key, default) ?: "",
            title = { Text(setting.title) },
            checkTextValid = checkTextValid
        )
    }
}
