package com.isakaro.kwik.catalog.colors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.helpers.KwikCenterColumn
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
fun KwikColorsScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ScrollableShowCaseContainer(
        title = "Color scheme",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ColorShowCase(title = "Primary color", color = MaterialTheme.colorScheme.primary)
        ColorShowCase(title = "On primary color", color = MaterialTheme.colorScheme.onPrimary)
        ColorShowCase(title = "Primary container", color = MaterialTheme.colorScheme.primaryContainer)
        ColorShowCase(title = "On primary container", color = MaterialTheme.colorScheme.onPrimaryContainer)

        ColorShowCase(title = "Secondary color", color = MaterialTheme.colorScheme.secondary)
        ColorShowCase(title = "On secondary color", color = MaterialTheme.colorScheme.onSecondary)
        ColorShowCase(title = "Secondary container", color = MaterialTheme.colorScheme.secondaryContainer)
        ColorShowCase(title = "On secondary container", color = MaterialTheme.colorScheme.onSecondaryContainer)

        ColorShowCase(title = "Tertiary color", color = MaterialTheme.colorScheme.tertiary)
        ColorShowCase(title = "On tertiary color", color = MaterialTheme.colorScheme.onTertiary)
        ColorShowCase(title = "Tertiary container", color = MaterialTheme.colorScheme.tertiaryContainer)
        ColorShowCase(title = "On tertiary container", color = MaterialTheme.colorScheme.onTertiaryContainer)

        ColorShowCase(title = "Background", color = MaterialTheme.colorScheme.background)
        ColorShowCase(title = "On background", color = MaterialTheme.colorScheme.onBackground)

        ColorShowCase(title = "Surface", color = MaterialTheme.colorScheme.surface)
        ColorShowCase(title = "On surface", color = MaterialTheme.colorScheme.onSurface)
        ColorShowCase(title = "Surface variant", color = MaterialTheme.colorScheme.surfaceVariant)
        ColorShowCase(title = "On surface variant", color = MaterialTheme.colorScheme.onSurfaceVariant)

        ColorShowCase(title = "Error", color = MaterialTheme.colorScheme.error)
        ColorShowCase(title = "On error", color = MaterialTheme.colorScheme.onError)
        ColorShowCase(title = "Error container", color = MaterialTheme.colorScheme.errorContainer)
        ColorShowCase(title = "On error container", color = MaterialTheme.colorScheme.onErrorContainer)

        ColorShowCase(title = "Outline", color = MaterialTheme.colorScheme.outline)
        ColorShowCase(title = "Outline variant", color = MaterialTheme.colorScheme.outlineVariant)

        ColorShowCase(title = "Inverse surface", color = MaterialTheme.colorScheme.inverseSurface)
        ColorShowCase(title = "Inverse on surface", color = MaterialTheme.colorScheme.inverseOnSurface)
        ColorShowCase(title = "Inverse primary", color = MaterialTheme.colorScheme.inversePrimary)

        ColorShowCase(title = "Surface tint", color = MaterialTheme.colorScheme.surfaceTint)
        ColorShowCase(title = "Scrim", color = MaterialTheme.colorScheme.scrim)
    }

}

@Composable
private fun ColorShowCase(
    title: String,
    color: Color
) {
    KwikCenterColumn {
        KwikText.BodyLarge(
            text = title
        )

        Box(
            modifier = Modifier
                .background(color = color, shape = MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .height(30.dp)
        )
    }

    KwikVSpacer(12)
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikColorsScreen() {
    KwikTheme {
        KwikColorsScreen()
    }
}
