// SPDX-License-Identifier: GPL-3.0-only
package org.idumishmi.keyboard.settings

import android.content.Context
import org.idumishmi.keyboard.keyboard.internal.KeyboardIconsSet
import org.idumishmi.keyboard.latin.settings.Settings
import org.idumishmi.keyboard.latin.utils.SubtypeSettings

// file is meant for making compose previews work

fun initPreview(context: Context) {
    Settings.init(context)
    SubtypeSettings.init(context)
    SettingsActivity.settingsContainer = SettingsContainer(context)
    KeyboardIconsSet.instance.loadIcons(context)
}

const val previewDark = true
