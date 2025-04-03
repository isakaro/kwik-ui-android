package com.isakaro.qwik.catalog.slider

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.navigator
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun SliderScreen(
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
private fun PreviewStartScreen() {
    QwikTheme {
        SliderScreen()
    }
}
