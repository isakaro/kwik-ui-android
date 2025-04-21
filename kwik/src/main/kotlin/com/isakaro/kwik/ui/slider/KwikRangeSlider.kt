package com.isakaro.kwik.ui.slider

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * A range slider component
 *
 * @param value ClosedFloatingPointRange<Float> value of the slider
 * @param onValueChange (ClosedFloatingPointRange<Float>) -> Unit called when the value of the slider changes
 * @param valueRange ClosedFloatingPointRange<Float> range of the slider
 * @param steps Int number of steps in the slider
 * @param enabled Boolean whether the slider is enabled or not
 * @param startThumb @Composable () -> Unit custom start thumb
 * @param endThumb @Composable () -> Unit custom end thumb
 * @param track @Composable (RangeSliderState) -> Unit custom track
 *
 *
 * Usage:
 * ```
 * var sliderPosition by remember { mutableStateOf(20f..80f) }
 *
 * KwikRangeSlider(
 *      value = sliderPosition,
 *      onValueChange = { range ->
 *          sliderPosition = range
 *      }
 *  )
 *  ```
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikRangeSlider(
    modifier: Modifier = Modifier,
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    steps: Int = 99,
    enabled: Boolean = true,
    startThumb: @Composable () -> Unit = {
        SliderDefaults.Thumb(
            interactionSource = remember { MutableInteractionSource() },
            thumbSize = DpSize(10.dp, 25.dp)
        )
    },
    endThumb: @Composable () -> Unit = {
        SliderDefaults.Thumb(
            interactionSource = remember { MutableInteractionSource() },
            thumbSize = DpSize(10.dp, 25.dp)
        )
    },
    track: @Composable (RangeSliderState) -> Unit = { rangeSliderState ->
        SliderDefaults.Track(
            rangeSliderState = rangeSliderState,
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
    RangeSlider(
        modifier = modifier,
        value = value,
        onValueChange = { range ->
            onValueChange(range)
        },
        valueRange = valueRange,
        enabled = enabled,
        steps = steps,
        track = {
            track(it)
        },
        startThumb = {
            startThumb()
        },
        endThumb = {
            endThumb()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun KwikRangeSliderPreview() {
    var sliderPosition by remember { mutableStateOf(20f..80f) }

    KwikRangeSlider(
        value = sliderPosition,
        onValueChange = { range ->
            sliderPosition = range
        }
    )
}