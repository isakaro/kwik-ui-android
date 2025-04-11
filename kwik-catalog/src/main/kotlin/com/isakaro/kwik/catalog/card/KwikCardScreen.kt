package com.isakaro.kwik.catalog.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.KwikCard
import com.isakaro.kwik.KwikImageCard
import com.isakaro.kwik.KwikImageCardHorizontal
import com.isakaro.kwik.KwikText
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikCardScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ScrollableShowCaseContainer(
        title = "Card",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Card") {
            KwikCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "I am a Card",
                        color = Color.Black,
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        ShowCase(title = "Card with image and title (vertical)") {
            KwikImageCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                image = "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
                title = "I am a Card with an image",
            ){
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    repeat(5){
                        KwikText.BodyLarge(
                            text = "Content",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        ShowCase(title = "Card with image (horizontal)") {
            KwikImageCardHorizontal(
                modifier = Modifier.fillMaxWidth(),
                image = "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
            ){
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    KwikText.TitleSmall(text = "Tortuga")

                    KwikText.BodyMedium(
                        text = "A lawless place, a pirate haven, and a refuge for the most notorious pirates of the Caribbean.",
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikCardScreen() {
    KwikCardScreen()
}
