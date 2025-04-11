package com.isakaro.kwik.catalog.carousel

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikImageCarousel
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikCarouselScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Carousel",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Image carousel slider") {
            KwikImageCarousel(
                images = listOf(
                    "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
                    "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
                    "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
                    "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0"
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikCarouselScreen() {
    KwikCarouselScreen()
}
