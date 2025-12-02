package com.isakaro.kwik.catalog.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.utils.KwikConstants
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.card.KwikCard
import com.isakaro.kwik.ui.card.KwikImageCard
import com.isakaro.kwik.ui.card.KwikImageCardHorizontal
import com.isakaro.kwik.ui.helpers.KwikCenterColumn
import com.isakaro.kwik.ui.text.KwikText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
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
                KwikCenterColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    KwikText.BodyLarge(
                        text = "I am a Card",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        ShowCase(title = "Card with image and title (vertical)") {
            KwikImageCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                image = KwikConstants.SAMPLE_IMAGE,
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
                modifier = Modifier.fillMaxWidth().height(150.dp),
                image = KwikConstants.SAMPLE_IMAGE,
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
