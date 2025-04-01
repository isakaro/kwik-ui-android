package com.isakaro.ui.catalog.progress

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.ui.catalog.common.ShowCase
import com.isakaro.ui.catalog.common.ShowCaseContainer
import app.isakaro.ui.library.theme.Theme.AmpersandTheme

@Composable
internal fun ProgressIndicatorScreen() {
    ShowCaseContainer {
        ShowCase(title = "Progress Indicator Linear") {
            LinearProgressIndicator(
                trackColor = Color.Gray,
            )
        }
        ShowCase(title = "Progress Indicator Circular") {
            CircularProgressIndicator(
                trackColor = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        ProgressIndicatorScreen()
    }
}
