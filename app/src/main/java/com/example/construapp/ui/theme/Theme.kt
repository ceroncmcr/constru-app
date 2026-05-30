package com.example.construapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = SurfaceLight,
    primaryContainer = OrangeContainer,
    onPrimaryContainer = OnOrangeContainer,
    secondary = GrayDark,
    surface = SurfaceLight,
    onSurface = GrayDark,
    onSurfaceVariant = GrayMedium,
    outline = OutlineLight,
    error = DangerRed
)

private val DarkColorScheme = darkColorScheme(
    primary = OrangePrimary,
    onPrimary = SurfaceLight,
    primaryContainer = OrangeDark,
    secondary = GrayMedium,
    error = DangerRed
)

@Composable
fun ConstruAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
