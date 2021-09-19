package com.alpha2048.jetpackcomposetest.ui_component.resource

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = lightThemaPrimary,
    primaryVariant = lightThemaPrimaryVariant,
    onPrimary = Color.Black,
    secondary = lightThemaPrimary,
    secondaryVariant = lightThemaPrimaryVariant,
    onSecondary = Color.Black,
    error = Color.Black,
    background = lightThemaBackground,
)

private val DarkThemeColors = darkColors(
    primary = darkThemaPrimary,
    primaryVariant = darkThemaPrimaryVariant,
    onPrimary = Color.White,
    secondary = darkThemaPrimary,
    onSecondary = Color.White,
    error = Color.White,
    background = darkThemaBackground,
)

@Composable
fun MyThema(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}
