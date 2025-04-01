package com.isakaro.qwik.catalog.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.catalog.common.ShowCase
import com.isakaro.qwik.catalog.common.ShowCaseContainer
import com.isakaro.qwik.theme.Theme.AmpersandTheme

@Composable
internal fun ShapeScreen() {
    ShowCaseContainer {
        ShapeSmall()
        ShapeMedium()
        ShapeLarge()
    }
}

@Composable
private fun ShapeSmall() {
    MaterialShape(
        name = "Small",
        shape = MaterialTheme.shapes.small
    )
}

@Composable
private fun ShapeMedium() {
    MaterialShape(
        name = "Medium",
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
private fun ShapeLarge() {
    MaterialShape(
        name = "large",
        shape = MaterialTheme.shapes.large
    )
}

@Composable
private fun MaterialShape(name: String, shape: Shape) {
    ShowCase(name) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            onClick = {}
        ) {
            Text(text = "action", color = Color.Black, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShapeScreen() {
    AmpersandTheme {
        ShapeScreen()
    }
}
