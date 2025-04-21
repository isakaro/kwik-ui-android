package com.isakaro.kwik.catalog.rating

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.rating.KwikRatingBar
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
fun KwikRatingBarScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ShowCaseContainer(
        title = "Rating bar",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(
            title = "Rating display bar"
        ) {
            KwikRatingBar(
                stars = 5,
                rating = 5.0
            )
        }

        ShowCase(
            title = "Rating display bar without badge"
        ) {
            KwikRatingBar(
                rating = 3.0,
                showBadge = false
            )
        }

        ShowCase(
            title = "Rating bar that accepts user input",
        ) {
            var userRating by remember { mutableIntStateOf(0) }

            KwikRatingBar(
                stars = 5,
                rating = userRating. toDouble(),
                clickable = true,
                showBadge = false,
                starSize = 60.dp,
                onClick = { selectedRating ->
                    userRating = selectedRating
                }
            )

            KwikText.BodyMedium(
                text = "User rating: $userRating",
            )
        }
    }

}

@Preview
@Composable
private fun PreviewKwikRatingBarScreen() {
    KwikRatingBarScreen()
}