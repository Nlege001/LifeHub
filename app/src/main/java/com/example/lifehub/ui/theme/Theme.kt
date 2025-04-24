package com.example.lifehub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = Lavender,
    secondary = Indigo,
    background = Charcoal,
    surface = Slate,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = WarmGray
)

private val LightColors = lightColorScheme(
    primary = Indigo,
    secondary = Lavender,
    background = WarmGray,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun LifeHubTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LifeHubTypography,
        content = content
    )
}