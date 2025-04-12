package com.isakaro.kwik.catalog.carousel

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.carousel.KwikCarousel
import com.isakaro.kwik.carousel.KwikImageCarousel
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.text.KwikText
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
        ShowCase(title = "Image carousel") {
            KwikImageCarousel(
                modifier = Modifier.height(200.dp),
                images = listOf(
                    "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
                    "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
                    "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
                    "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0"
                )
            )
        }

        ShowCase(title = "Content carousel") {
            val items = listOf(
                KwikText.TitleMedium(
                    text = "Item 1"
                ),
                KwikText.TitleMedium(
                    text = "Item 2"
                ),
                KwikText.TitleMedium(
                    text = "Item 3"
                )
            )

            KwikCarousel(
                itemCount = items.size
            ){ page ->
                items[page]
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikCarouselScreen() {
    KwikCarouselScreen()
}
