// SPDX-License-Identifier: GPL-3.0-only
package org.idumishmi.keyboard.settings.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.idumishmi.keyboard.latin.R
import org.idumishmi.keyboard.latin.utils.JniUtils
import org.idumishmi.keyboard.latin.utils.SubtypeSettings
import org.idumishmi.keyboard.latin.utils.displayName
import org.idumishmi.keyboard.settings.NextScreenIcon
import org.idumishmi.keyboard.settings.preferences.Preference
import org.idumishmi.keyboard.settings.SearchSettingsScreen
import org.idumishmi.keyboard.settings.Theme
import org.idumishmi.keyboard.settings.initPreview
import org.idumishmi.keyboard.settings.previewDark

@Composable
fun MainSettingsScreen(
    onClickAbout: () -> Unit,
    onClickPreferences: () -> Unit,
    onClickToolbar: () -> Unit,
    onClickGestureTyping: () -> Unit,
    onClickAdvanced: () -> Unit,
    onClickAppearance: () -> Unit,
    onClickLayouts: () -> Unit,
    onClickBack: () -> Unit,
) {
    val ctx = LocalContext.current
    SearchSettingsScreen(
        onClickBack = onClickBack,
        title = stringResource(R.string.ime_settings),
        settings = emptyList(),
    ) {
        val enabledSubtypes = SubtypeSettings.getEnabledSubtypes(true)
        Column(Modifier.verticalScroll(rememberScrollState())) {
            // Removed onClickLanguage as it is no longer supported
            // Preference(
            //    name = stringResource(R.string.language_and_layouts_title),
            //    description = enabledSubtypes.joinToString(", ") { it.displayName(ctx) },
            //    onClick = onClickLanguage,
            //    icon = R.drawable.ic_settings_languages
            // ) { NextScreenIcon() }
            Preference(
                name = stringResource(R.string.settings_screen_preferences),
                onClick = onClickPreferences,
                icon = R.drawable.ic_settings_preferences
            ) { NextScreenIcon() }
            Preference(
                name = stringResource(R.string.settings_screen_appearance),
                onClick = onClickAppearance,
                icon = R.drawable.ic_settings_appearance
            ) { NextScreenIcon() }
            Preference(
                name = stringResource(R.string.settings_screen_toolbar),
                onClick = onClickToolbar,
                icon = R.drawable.ic_settings_toolbar
            ) { NextScreenIcon() }
            if (JniUtils.sHaveGestureLib)
                Preference(
                    name = stringResource(R.string.settings_screen_gesture),
                    onClick = onClickGestureTyping,
                    icon = R.drawable.ic_settings_gesture
                ) { NextScreenIcon() }
            // Removed onClickTextCorrection as it is no longer supported
            // Preference(
            //    name = stringResource(R.string.settings_screen_correction),
            //    onClick = onClickTextCorrection,
            //    icon = R.drawable.ic_settings_correction
            // ) { NextScreenIcon() }
            Preference(
                name = stringResource(R.string.settings_screen_secondary_layouts),
                onClick = onClickLayouts,
                icon = R.drawable.ic_ime_switcher
            ) { NextScreenIcon() }
            // Removed onClickDictionaries as it is no longer supported
            // Preference(
            //    name = stringResource(R.string.dictionary_settings_category),
            //    onClick = onClickDictionaries,
            //    icon = R.drawable.ic_dictionary
            // ) { NextScreenIcon() }
            Preference(
                name = stringResource(R.string.settings_screen_advanced),
                onClick = onClickAdvanced,
                icon = R.drawable.ic_settings_advanced
            ) { NextScreenIcon() }
            Preference(
                name = stringResource(R.string.settings_screen_about),
                onClick = onClickAbout,
                icon = R.drawable.ic_settings_about
            ) { NextScreenIcon() }
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    initPreview(LocalContext.current)
    Theme(previewDark) {
        Surface {
            MainSettingsScreen({}, {}, {}, {}, {}, {}, {}, {}) // Updated call site
        }
    }
}
