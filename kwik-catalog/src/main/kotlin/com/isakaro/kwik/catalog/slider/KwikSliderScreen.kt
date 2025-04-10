package com.isakaro.kwik.catalog.slider

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikSliderScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Radio button",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Slider") {
            var sliderPosition by remember { mutableFloatStateOf(0f) }

            Text(text = sliderPosition.toString())

            Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
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
