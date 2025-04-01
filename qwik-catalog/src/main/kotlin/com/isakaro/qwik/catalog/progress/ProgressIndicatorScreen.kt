package com.isakaro.qwik.catalog.progress

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikCircularLoading
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.theme.Theme.QwikTheme

@Composable
internal fun ProgressIndicatorScreen() {
    ShowCaseContainer {
        ShowCase(title = "Progress Indicator Linear") {
            LinearProgressIndicator(
                trackColor = Color.White
            )
        }
        ShowCase(title = "Progress Indicator Circular") {
            QwikCircularLoading(
                trackColor = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    QwikTheme {
        ProgressIndicatorScreen()
    }
}
