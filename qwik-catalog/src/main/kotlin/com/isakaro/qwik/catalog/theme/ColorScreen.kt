package com.isakaro.qwik.catalog.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.catalog.common.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.common.ShowCase
import com.isakaro.qwik.theme.Theme.AmpersandTheme

import java.util.Locale.ENGLISH

@Composable
internal fun ColorScreen() {
    ScrollableShowCaseContainer {
        PrimaryColor()
        PrimaryVariantColor()
        SecondaryColor()
        SecondaryVariantColor()
        BackgroundColor()
        SurfaceColor()
        ErrorColor()
        OnPrimaryColor()
        OnSecondaryColor()
        OnBackgroundColor()
        OnSurfaceColor()
        OnErrorColor()
    }
}

@Composable
private fun OnErrorColor() {
    MaterialColor(
        name = "OnError Color",
        color = MaterialTheme.colorScheme.onError
    )
}

@Composable
private fun OnSurfaceColor() {
    MaterialColor(
        name = "OnSurface Color",
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun OnBackgroundColor() {
    MaterialColor(
        name = "OnBackground Color",
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun OnSecondaryColor() {
    MaterialColor(
        name = "OnSecondary Color",
        color = MaterialTheme.colorScheme.onSecondary
    )
}

@Composable
private fun OnPrimaryColor() {
    MaterialColor(
        name = "OnPrimary Color",
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
private fun ErrorColor() {
    MaterialColor(
        name = "Error Color",
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
private fun SurfaceColor() {
    MaterialColor(
        name = "Surface Color",
        color = MaterialTheme.colorScheme.surface
    )
}

@Composable
private fun BackgroundColor() {
    MaterialColor(
        name = "Background Color",
        color = MaterialTheme.colorScheme.background
    )
}

@Composable
private fun SecondaryVariantColor() {
    MaterialColor(
        name = "Secondary Variant Color",
        color = MaterialTheme.colorScheme.onSecondary
    )
}

@Composable
private fun SecondaryColor() {
    MaterialColor(
        name = "Secondary Color",
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
private fun PrimaryVariantColor() {
    MaterialColor(
        name = "Primary Variant Color",
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
private fun PrimaryColor() {
    MaterialColor(
        name = "Primary Color",
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun MaterialColor(name: String, color: Color) {
    ShowCase(name) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color)
                    .border(1.dp, Color.Magenta)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = color.toHexCode(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun Color.toHexCode(): String {
    val tempAlpha = alpha * 255
    val tempRed = red * 255
    val tempGreen = green * 255
    val tempBlue = blue * 255
    return String.format(ENGLISH, "#%02x%02x%02x%02x", tempAlpha.toInt(), tempRed.toInt(), tempGreen.toInt(), tempBlue.toInt())
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        ColorScreen()
    }
}
