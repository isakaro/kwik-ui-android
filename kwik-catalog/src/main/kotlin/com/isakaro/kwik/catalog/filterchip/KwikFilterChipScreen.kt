package com.isakaro.kwik.catalog.filterchip

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikFilterChipScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ScrollableShowCaseContainer(
        title = "Kwik filter chip",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikFilterChipScreen() {
    KwikTheme {
        KwikFilterChipScreen()
    }
}
