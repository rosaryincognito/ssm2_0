package com.subaru.ssm2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = SubaruBlue,
    secondary = SubaruBlue,
    background = SubaruDark,
    surface = SubaruGray,
    onPrimary = White,
    onBackground = White,
    onSurface = White
)

private val LightColors = lightColorScheme(
    primary = SubaruBlue,
    secondary = SubaruBlue
)

@Composable
fun SubaruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}