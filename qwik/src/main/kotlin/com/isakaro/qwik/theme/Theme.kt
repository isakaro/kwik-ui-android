package com.isakaro.qwik.theme

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
    fun QwikTheme(
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
    primary = PrimaryColor,
    secondary = Color.White,
    error = ErrorColor,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = ErrorColor
)

internal val DarkColorPalette = darkColorScheme(
    primary = PrimaryColor,
    secondary = Color.White,
    surface = Color.Black,
    background = Color.Black,
    error = ErrorColor,
    onPrimary = Color.LightGray,
    onSecondary = Color.Black,
    onBackground = Color.Gray,
    onSurface = Color.Gray,
    onError = ErrorColor
)