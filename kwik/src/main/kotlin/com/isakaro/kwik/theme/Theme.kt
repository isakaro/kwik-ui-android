package com.isakaro.kwik.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Theme {
    @Composable
    fun KwikTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = false,
        content: @Composable () -> Unit
    ) {
        val colorScheme: ColorScheme = if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }

        val shapes = Shapes(
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(8.dp),
            large = RoundedCornerShape(16.dp),
            extraLarge = RoundedCornerShape(24.dp)
        )

        MaterialTheme(
            colorScheme = LightColorPalette,
            typography = Typography,
            shapes = shapes,
            content = content
        )
    }
}

internal val LightColorPalette = lightColorScheme(
    primary = KwikColorPrimary,
    secondary = Color.White,
    error = KwikColorError,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = KwikColorError
)

internal val DarkColorPalette = darkColorScheme(
    primary = KwikColorPrimary,
    secondary = Color.White,
    surface = Color.Black,
    background = Color.Black,
    error = KwikColorError,
    onPrimary = Color.LightGray,
    onSecondary = Color.Black,
    onBackground = Color.Gray,
    onSurface = Color.Gray,
    onError = KwikColorError
)