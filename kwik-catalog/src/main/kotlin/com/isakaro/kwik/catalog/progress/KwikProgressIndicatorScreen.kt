package com.isakaro.kwik.catalog.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.ui.loading.KwikCircularLoading
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.ui.loading.KwikLinearLoading
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikProgressIndicatorScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Progress",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Progress Indicator Linear") {
            KwikLinearLoading(
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
private fun PreviewKwikProgressIndicatorScreen() {
    KwikTheme {
        KwikProgressIndicatorScreen()
    }
}
