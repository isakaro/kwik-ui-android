package com.isakaro.kwik.catalog.carousel

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.button.KwikIconButton
import com.isakaro.kwik.carousel.KwikCarousel
import com.isakaro.kwik.carousel.KwikCarouselState
import com.isakaro.kwik.carousel.KwikImageCarousel
import com.isakaro.kwik.carousel.next
import com.isakaro.kwik.carousel.previous
import com.isakaro.kwik.carousel.rememberKwikCarouselState
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
        val images = listOf(
            "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
            "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
            "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
            "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0"
        )

        ShowCase(title = "Image carousel") {
            val carouselState = rememberKwikCarouselState(
                KwikCarouselState(
                    itemCount = 3,
                    loop = true
                )
            )

            KwikImageCarousel(
                modifier = Modifier.height(200.dp),
                state = carouselState,
                images = images
            )
        }

        ShowCase(title = "Image carousel with autoplay and looping capabilities") {
            val carouselState = rememberKwikCarouselState(
                KwikCarouselState(
                    itemCount = 3,
                    loop = true
                )
            )

            KwikImageCarousel(
                modifier = Modifier.height(200.dp),
                state = carouselState,
                autoPlay = true,
                images = images
            )
        }

        ShowCase(title = "Image carousel with custom navigation buttons") {
            val carouselState = rememberKwikCarouselState(KwikCarouselState(itemCount = images.size))

            KwikImageCarousel(
                modifier = Modifier.height(200.dp),
                state = carouselState,
                images = images,
                customPrevButton = {
                    KwikIconButton(
                        icon = Icons.Default.Add
                    ) {
                        carouselState.next()
                    }
                },
                customNextButton = {
                    KwikIconButton(
                        icon = Icons.Default.Close
                    ) {
                        carouselState.previous()
                    }
                }
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

            val carouselState = rememberKwikCarouselState(KwikCarouselState(itemCount = items.size))

            KwikCarousel(
                state = carouselState
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
