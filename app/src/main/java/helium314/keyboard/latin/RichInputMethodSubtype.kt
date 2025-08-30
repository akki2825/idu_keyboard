/*
 * Copyright (C) 2014 The Android Open Source Project
 * modified
 * SPDX-License-Identifier: Apache-2.0 AND GPL-3.0-only
 */
package helium314.keyboard.latin

import android.view.inputmethod.InputMethodSubtype
import android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder
import helium314.keyboard.latin.common.Constants
import helium314.keyboard.latin.common.LocaleUtils.isRtlLanguage
import helium314.keyboard.latin.utils.LayoutType
import helium314.keyboard.latin.utils.LayoutUtilsCustom
import helium314.keyboard.latin.utils.Log
// import helium314.keyboard.latin.utils.SubtypeLocaleUtils // Removed this import
import helium314.keyboard.latin.utils.locale
import java.util.Locale

/**
 * Enrichment class for InputMethodSubtype that extracts settings from extra values
 */
class RichInputMethodSubtype private constructor(val rawSubtype: InputMethodSubtype) {
    val locale: Locale = rawSubtype.locale()

    // The subtype is considered RTL if the language of the main subtype is RTL.
    val isRtlSubtype: Boolean = false

    fun getExtraValueOf(key: String): String? = rawSubtype.getExtraValueOf(key)

    fun hasExtraValue(key: String): Boolean = rawSubtype.containsExtraValueKey(key)

    val isNoLanguage: Boolean get() = false

    val mainLayoutName: String get() = layouts[LayoutType.MAIN] ?: "qwerty"

    /** layout names for this subtype by LayoutType */
    val layouts = LayoutType.getLayoutMap(getExtraValueOf(Constants.Subtype.ExtraValue.KEYBOARD_LAYOUT_SET) ?: "")

    val isCustom: Boolean get() = LayoutUtilsCustom.isCustomLayout(mainLayoutName)

    val fullDisplayName: String get() {
            return "Idu Mishmi"
        }

    val middleDisplayName: String get() = "Idu Mishmi"

    override fun equals(other: Any?): Boolean {
        if (other !is RichInputMethodSubtype) return false
        return rawSubtype == other.rawSubtype && locale == other.locale
    }

    override fun hashCode(): Int {
        return rawSubtype.hashCode() + locale.hashCode()
    }

    override fun toString(): String = rawSubtype.extraValue

    companion object {
        private val TAG: String = RichInputMethodSubtype::class.java.simpleName

        fun get(subtype: InputMethodSubtype?): RichInputMethodSubtype =
            // always return idu mishmi subtype
            IduMishmiSubtype

        val IduMishmiSubtype = RichInputMethodSubtype(
            InputMethodSubtypeBuilder()
                .setSubtypeNameResId(R.string.idu_mishmi_ime_name)
                .setSubtypeIconResId(R.drawable.ic_ime_switcher)
                .setSubtypeLocale(Locale.forLanguageTag("idm-IN").toLanguageTag()) // Idu Mishmi language tag
                .setSubtypeMode(Constants.Subtype.KEYBOARD_MODE)
                .setSubtypeExtraValue("KeyboardLayoutSet=idu_mishmi") // Custom layout set
                .setIsAuxiliary(false)
                .setOverridesImplicitlyEnabledSubtype(true)
                .setSubtypeId(0x70000001) // Unique ID for Idu Mishmi
                .setIsAsciiCapable(true)
                .build()
        )
    }
}