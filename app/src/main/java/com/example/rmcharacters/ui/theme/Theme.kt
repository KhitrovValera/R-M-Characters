package com.example.rmcharacters.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccent,
    surface = DarkCard,
    background = DarkGradientTop,
    secondary = DarkGradientBottom,
    onSurface = Color.White,
    onSurfaceVariant = Color.LightGray,
    surfaceContainer = DarkBorder,
    primaryContainer = DarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = LightAccent,
    surface = LightCard,
    background = LightGradientTop,
    secondary = LightGradientBottom,
    onSurface = Color.Black,
    onSurfaceVariant = Color.DarkGray,
    surfaceContainer = LightBorder,
    primaryContainer = LightCharacterCardBackground
)

@Composable
fun RMCharactersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}