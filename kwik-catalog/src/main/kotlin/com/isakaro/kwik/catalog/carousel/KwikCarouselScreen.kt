package com.isakaro.kwik.catalog.carousel

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.utils.KwikConstants
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.button.KwikIconButton
import com.isakaro.kwik.ui.card.KwikCard
import com.isakaro.kwik.ui.carousel.KwikCarousel
import com.isakaro.kwik.ui.carousel.KwikCarouselState
import com.isakaro.kwik.ui.carousel.KwikImageCarousel
import com.isakaro.kwik.ui.carousel.next
import com.isakaro.kwik.ui.carousel.previous
import com.isakaro.kwik.ui.carousel.rememberKwikCarouselState
import com.isakaro.kwik.ui.carousel.slideTo
import com.isakaro.kwik.ui.helpers.KwikCenterColumn
import com.isakaro.kwik.ui.text.KwikText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikCarouselScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ScrollableShowCaseContainer(
        title = "Carousel",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        val images = listOf(
            KwikConstants.SAMPLE_IMAGE,
            KwikConstants.SAMPLE_IMAGE,
            KwikConstants.SAMPLE_IMAGE,
            KwikConstants.SAMPLE_IMAGE
        )

        ShowCase(title = "Image carousel") {
            val carouselState = rememberKwikCarouselState(
                KwikCarouselState(
                    itemCount = images.size
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
                    itemCount = images.size,
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
                prevButton = {
                    KwikIconButton(
                        icon = Icons.Default.Close
                    ) {
                        carouselState.previous()
                    }
                },
                nextButton = {
                    KwikIconButton(
                        icon = Icons.Default.Add
                    ) {
                        carouselState.next()
                    }
                }
            )
        }

        ShowCase(title = "Custom content carousel") {
            val content: List<@Composable () -> Unit> = listOf(
                {
                    KwikCard(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Green
                    ) {
                        KwikCenterColumn {
                            KwikText.TitleMedium(
                                text = "Content One"
                            )
                        }
                    }
                },
                {
                    KwikCard(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Yellow
                    ) {
                        KwikCenterColumn {
                            KwikText.TitleMedium(
                                text = "Content Two"
                            )
                        }
                    }
                },
                {
                    KwikCard(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Blue
                    ) {
                        KwikCenterColumn {
                            KwikText.TitleMedium(
                                text = "Content Three"
                            )
                        }
                    }
                }
            )

            val carouselState = rememberKwikCarouselState(KwikCarouselState(itemCount = content.size))

            KwikCarousel(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                state = carouselState,
                showNavigation = false,
                contentBuilder = { page ->
                    content[page].invoke()
                }
            )
        }

        ShowCase(title = "Image carousel with slide to specific index") {
            val carouselStateRandom = rememberKwikCarouselState(KwikCarouselState(itemCount = images.size))
            var currentIndex by remember { mutableIntStateOf(0) }

            KwikImageCarousel(
                modifier = Modifier.height(200.dp),
                state = carouselStateRandom,
                images = images
            )

            KwikButton(
                text = "Slide randomly",
                onClick = {
                    val value = (0..images.size - 1).random()
                    currentIndex = value
                }
            )

            LaunchedEffect(currentIndex) {
                carouselStateRandom.slideTo(currentIndex)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikCarouselScreen() {
    KwikCarouselScreen()
}
