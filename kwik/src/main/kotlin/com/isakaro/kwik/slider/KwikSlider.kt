package com.isakaro.kwik.slider

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * A slider component
 *
 * @param value Float value of the slider. Default is 0f
 * @param onValueChange (Float) -> Unit called when the value of the slider changes
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikSlider(
    modifier : Modifier = Modifier,
    value: Float = 0f,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1.0f,
    enabled: Boolean = true
) {

    var sliderPosition by remember { mutableFloatStateOf(value) }
    val interactionSource = remember { MutableInteractionSource() }

    Slider(
        modifier = modifier,
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            onValueChange(it)
        },
        interactionSource = interactionSource,
        valueRange = valueRange,
        enabled = enabled,
        track = {
            SliderDefaults.Track(
                sliderState = it,
                modifier = Modifier.height(6.dp),
                thumbTrackGapSize = 0.dp,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = Color.LightGray,
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent
                )
            )
        },
        thumb = {
            SliderDefaults.Thumb(
                interactionSource = interactionSource,
                thumbSize = DpSize(10.dp, 25.dp)
            )
        }
    )

}