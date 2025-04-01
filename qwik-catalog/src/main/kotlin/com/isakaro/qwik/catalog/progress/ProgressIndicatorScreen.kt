package com.isakaro.qwik.catalog.progress

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.catalog.common.ShowCase
import com.isakaro.qwik.catalog.common.ShowCaseContainer
import com.isakaro.qwik.theme.Theme.AmpersandTheme

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
