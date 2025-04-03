package com.isakaro.kwik.catalog.progress

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikCircularLoading
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.Theme.KwikTheme
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
            KwikCircularLoading(
                trackColor = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStartScreen() {
    KwikTheme {
        ProgressIndicatorScreen()
    }
}
