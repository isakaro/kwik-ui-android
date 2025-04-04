package com.isakaro.kwik.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikRatingBar
import com.isakaro.kwik.navigator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun KwikRatingBarScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ShowCaseContainer(
        title = "Rating bar",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        KwikRatingBar(
            stars = 5,
            rating = 5.0
        )
    }

}

@Preview
@Composable
private fun PreviewKwikRatingBarScreen() {
    KwikRatingBarScreen()
}