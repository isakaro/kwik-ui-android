package com.isakaro.ui.catalog.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.ui.catalog.common.ScrollableShowCaseContainer
import com.isakaro.ui.catalog.common.ShowCase
import app.isakaro.ui.library.theme.Theme.AmpersandTheme

@Composable
internal fun TypographyScreen() {
    ScrollableShowCaseContainer {
        TextStyle_Body1()
        TextStyle_Body2()
        TextStyle_Body3()
        TextStyle_headlineLarge()
        TextStyle_headlineMedium()
        TextStyle_headlineSmall()
        TextStyle_labelLarge()
        TextStyle_labelMedium()
        TextStyle_labelSmall()
    }
}

@Composable
private fun TextStyle_headlineLarge() {
    MaterialTypography(
        name = "headlineLarge",
        textStyle = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun TextStyle_headlineMedium() {
    MaterialTypography(
        name = "headlineMedium",
        textStyle = MaterialTheme.typography.headlineMedium
    )
}

@Composable
private fun TextStyle_headlineSmall() {
    MaterialTypography(
        name = "headlineSmall",
        textStyle = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun TextStyle_Body1() {
    MaterialTypography(
        name = "body1",
        textStyle = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun TextStyle_Body2() {
    MaterialTypography(
        name = "body2",
        textStyle = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun TextStyle_Body3() {
    MaterialTypography(
        name = "body3",
        textStyle = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun TextStyle_labelLarge() {
    MaterialTypography(
        name = "labelLarge",
        textStyle = MaterialTheme.typography.labelLarge
    )
}

@Composable
private fun TextStyle_labelMedium() {
    MaterialTypography(
        name = "labelMedium",
        textStyle = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun TextStyle_labelSmall() {
    MaterialTypography(
        name = "labelSmall",
        textStyle = MaterialTheme.typography.labelSmall
    )
}

@Composable
private fun MaterialTypography(name: String, textStyle: TextStyle) {
    ShowCase(name) {
        Text(
            text = DUMMY,
            modifier = Modifier.fillMaxWidth(),
            style = textStyle,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTypographyScreen() {
    AmpersandTheme {
        TypographyScreen()
    }
}

private const val DUMMY: String = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et" +
    " dolore magna aliquyam erat, sed diam voluptua. "
