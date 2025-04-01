package com.isakaro.ui.catalog.slider

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.ui.catalog.common.ShowCase
import com.isakaro.ui.catalog.common.ShowCaseContainer
import app.isakaro.ui.library.theme.Theme.AmpersandTheme

@Composable
internal fun SliderScreen() {
    ShowCaseContainer {
        ShowCase(title = "Slider") {
            var sliderPosition by remember { mutableStateOf(0f) }
            Text(text = sliderPosition.toString())
            Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        SliderScreen()
    }
}
