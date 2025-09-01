// SPDX-License-Identifier: GPL-3.0-only
package org.idumishmi.keyboard.latin.utils

import android.content.Context
import org.idumishmi.keyboard.latin.settings.Defaults
import org.idumishmi.keyboard.latin.settings.Settings
import java.text.SimpleDateFormat
import java.util.Calendar

fun getTimestamp(context: Context): String {
    val format = context.prefs().getString(Settings.PREF_TIMESTAMP_FORMAT, Defaults.PREF_TIMESTAMP_FORMAT)
    val formatter = runCatching { SimpleDateFormat(format, Settings.getValues().mLocale) }.getOrNull()
        ?: SimpleDateFormat(Defaults.PREF_TIMESTAMP_FORMAT, Settings.getValues().mLocale)
    return formatter.format(Calendar.getInstance().time)
}

fun checkTimestampFormat(format: String) = runCatching { SimpleDateFormat(format, Settings.getValues().mLocale) }.isSuccess
