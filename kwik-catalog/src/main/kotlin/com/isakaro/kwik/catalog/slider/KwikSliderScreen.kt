package com.isakaro.kwik.catalog.slider

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikText
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.slider.KwikRangeSlider
import com.isakaro.kwik.slider.KwikSlider
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.roundToInt

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikSliderScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Sliders",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Range slider") {
            var sliderPosition by remember { mutableStateOf(20f..80f) }

            KwikRangeSlider(
                value = sliderPosition,
                onValueChange = { range ->
                    sliderPosition = range
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                KwikText.TitleSmall(
                    text = "Start: ${sliderPosition.start.roundToInt()}",
                    modifier = Modifier.weight(1f)
                )

                KwikText.TitleSmall(
                    text = "End: ${sliderPosition.endInclusive.roundToInt()}",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        ShowCase(title = "Slider") {
            var sliderPosition by remember { mutableFloatStateOf(0f) }

            KwikSlider(
                value = sliderPosition,
                onValueChange = { range ->
                    sliderPosition = range
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                KwikText.TitleSmall(
                    text = "Value: ${sliderPosition.roundToInt()}",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikSliderScreen() {
    KwikTheme {
        KwikSliderScreen()
    }
}
