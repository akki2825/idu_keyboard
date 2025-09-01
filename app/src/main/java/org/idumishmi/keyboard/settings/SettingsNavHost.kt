// SPDX-License-Identifier: GPL-3.0-only
package org.idumishmi.keyboard.settings

import android.provider.Settings
import android.provider.Settings.Global
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.idumishmi.keyboard.latin.common.LocaleUtils.constructLocale
import org.idumishmi.keyboard.latin.settings.SettingsSubtype.Companion.toSettingsSubtype
import org.idumishmi.keyboard.settings.screens.AboutScreen
import org.idumishmi.keyboard.settings.screens.AdvancedSettingsScreen
import org.idumishmi.keyboard.settings.screens.AppearanceScreen
import org.idumishmi.keyboard.settings.screens.ColorsScreen
import org.idumishmi.keyboard.settings.screens.DebugScreen
import org.idumishmi.keyboard.settings.screens.GestureTypingScreen
import org.idumishmi.keyboard.settings.screens.MainSettingsScreen
import org.idumishmi.keyboard.settings.screens.PreferencesScreen
import org.idumishmi.keyboard.settings.screens.SecondaryLayoutScreen
import org.idumishmi.keyboard.settings.screens.SubtypeScreen
import org.idumishmi.keyboard.settings.screens.ToolbarScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun SettingsNavHost(
    onClickBack: () -> Unit,
    startDestination: String? = null,
) {
    val navController = rememberNavController()
    val dir = if (LocalLayoutDirection.current == LayoutDirection.Ltr) 1 else -1
    val target = SettingsDestination.navTarget.collectAsState()

    // duration does not change when system setting changes, but that's rare enough to not care
    val duration = (250 * Settings.System.getFloat(LocalContext.current.contentResolver, Global.TRANSITION_ANIMATION_SCALE, 1f)).toInt()
    val animation = tween<IntOffset>(durationMillis = duration)

    fun goBack() {
        if (!navController.popBackStack()) onClickBack()
    }

    NavHost(
        navController = navController,
        startDestination = startDestination ?: SettingsDestination.Settings,
        enterTransition = { slideInHorizontally(initialOffsetX = { +it * dir }, animationSpec = animation) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it * dir }, animationSpec = animation) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it * dir }, animationSpec = animation) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { +it * dir }, animationSpec = animation) }
    ) {
        composable(SettingsDestination.Settings) {
            MainSettingsScreen(
                onClickAbout = { navController.navigate(SettingsDestination.About) },
                onClickPreferences = { navController.navigate(SettingsDestination.Preferences) },
                onClickToolbar = { navController.navigate(SettingsDestination.Toolbar) },
                onClickGestureTyping = { navController.navigate(SettingsDestination.GestureTyping) },
                onClickAdvanced = { navController.navigate(SettingsDestination.Advanced) },
                onClickAppearance = { navController.navigate(SettingsDestination.Appearance) },
                onClickLayouts = { navController.navigate(SettingsDestination.Layouts) },
                onClickBack = ::goBack,
            )
        }
        composable(SettingsDestination.About) {
            AboutScreen(onClickBack = ::goBack)
        }
        composable(SettingsDestination.Preferences) {
            PreferencesScreen(onClickBack = ::goBack)
        }
        composable(SettingsDestination.Toolbar) {
            ToolbarScreen(onClickBack = ::goBack)
        }
        composable(SettingsDestination.GestureTyping) {
            GestureTypingScreen(onClickBack = ::goBack)
        }
        composable(SettingsDestination.Advanced) {
            AdvancedSettingsScreen(onClickBack = ::goBack)
        }
        composable(SettingsDestination.Debug) {
            DebugScreen(onClickBack = ::goBack)
        }
        composable(SettingsDestination.Appearance) {
            AppearanceScreen(onClickBack = ::goBack)
        }
        composable(SettingsDestination.Layouts) {
            SecondaryLayoutScreen(onClickBack = ::goBack)
        }
        composable(SettingsDestination.Colors + "{theme}") {
            ColorsScreen(isNight = false, theme = it.arguments?.getString("theme"), onClickBack = ::goBack)
        }
        composable(SettingsDestination.ColorsNight + "{theme}") {
            ColorsScreen(isNight = true, theme = it.arguments?.getString("theme"), onClickBack = ::goBack)
        }
        composable(SettingsDestination.Subtype + "{subtype}") {
            SubtypeScreen(initialSubtype = it.arguments?.getString("subtype")!!.toSettingsSubtype(), onClickBack = ::goBack)
        }
    }
    if (target.value != SettingsDestination.Settings/* && target.value != navController.currentBackStackEntry?.destination?.route*/)
        navController.navigate(route = target.value)
}

object SettingsDestination {
    const val Settings = "settings"
    const val About = "about"
    const val Preferences = "preferences"
    const val Toolbar = "toolbar"
    const val GestureTyping = "gesture_typing"
    const val Advanced = "advanced"
    const val Debug = "debug"
    const val Appearance = "appearance"
    const val Colors = "colors/"
    const val ColorsNight = "colors_night/"
    const val Subtype = "subtype/"
    const val Layouts = "layouts"
    val navTarget = MutableStateFlow(Settings)

    private val navScope = CoroutineScope(Dispatchers.Default)
    fun navigateTo(target: String) {
        if (navTarget.value == target) {
            // triggers recompose twice, but that's ok as it's a rare event
            navTarget.value = Settings
            navScope.launch { delay(10); navTarget.value = target }
        } else
            navTarget.value = target
        navScope.launch { delay(50); navTarget.value = Settings }
    }
}
