package com.isakaro.kwik.ui.slider

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * A slider component
 *
 * @param value Float value of the slider. Default is 0f
 * @param onValueChange (Float) -> Unit called when the value of the slider changes
 * @param valueRange ClosedFloatingPointRange<Float> range of the slider
 * @param enabled Boolean whether the slider is enabled or not
 * @param thumb @Composable () -> Unit custom thumb
 * @param track @Composable (RangeSliderState) -> Unit custom track
 *
 * Usage:
 *
 * ```
 * var sliderPosition by remember { mutableFloatStateOf(10f) }
 *
 * KwikSlider(
 *     value = sliderPosition,
 *     onValueChange = { value ->
 *          sliderPosition = value
 *     }
 *)
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikSlider(
    modifier: Modifier = Modifier,
    value: Float = 10f,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    enabled: Boolean = true,
    thumb: @Composable () -> Unit = {
        SliderDefaults.Thumb(
            interactionSource = remember { MutableInteractionSource() },
            thumbSize = DpSize(10.dp, 25.dp)
        )
    },
    track: @Composable (SliderState) -> Unit = { sliderState ->
        SliderDefaults.Track(
            sliderState = sliderState,
            modifier = Modifier.height(6.dp),
            thumbTrackGapSize = 0.dp,
            colors = SliderDefaults.colors(
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = Color.LightGray,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )
    }
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
            track(it)
        },
        thumb = {
            thumb()
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun KwikSliderPreview() {
    var sliderPosition by remember { mutableFloatStateOf(10f) }

    KwikSlider(
        value = sliderPosition,
        onValueChange = { value ->
            sliderPosition = value
        }
    )
}