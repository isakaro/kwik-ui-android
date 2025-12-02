package com.isakaro.kwik.catalog.webview

import androidx.compose.runtime.Composable
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.webview.KwikWebView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
internal fun KwikWebViewScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Web view",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        KwikWebView(
            url = "https://wikipedia.org",
        )
    }
}

