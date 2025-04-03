package com.isakaro.qwik.catalog.progress

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikCircularLoading
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.navigator
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun ProgressIndicatorScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Progress",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
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
private fun PreviewStartScreen() {
    QwikTheme {
        ProgressIndicatorScreen()
    }
}
