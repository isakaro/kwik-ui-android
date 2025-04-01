package app.isakaro.ui.library.rangeslider

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
fun IsakaroRangeSlider(
    minimum: Int = 0,
    maximum: Int = 100,
    maximumRange: Int = 100,
    onMinimum: (Boolean) -> Unit = {},
    onMaximum: (Boolean) -> Unit = {},
    onChange: (Int, Int) -> Unit = { _, _ -> },
    maxText: String = "Max",
    minText: String = "Min"
) {
    var selectedMinimum by remember { mutableIntStateOf(0) }
    var selectedMaximum by remember { mutableIntStateOf(maximumRange) }
    var sliderPosition by remember { mutableStateOf(minimum.toFloat()..maximum.toFloat()) }

    LaunchedEffect(sliderPosition) {
        selectedMinimum = calculatePrice(0, maximumRange, sliderPosition.start.toInt())
        selectedMaximum = calculatePrice(0, maximumRange, sliderPosition.endInclusive.toInt())
        onChange(selectedMinimum, selectedMaximum)
        onMinimum(selectedMinimum == 0)
        onMaximum(selectedMaximum == maximumRange)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IsakaroRangeSlider(
            value = sliderPosition,
            onValueChange = { range ->
                sliderPosition = range
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Min",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = minText,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Max",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = maxText,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

internal fun calculatePrice(
    minPrice: Int,
    maxPrice: Int,
    sliderValue: Int,
    sliderMin: Int = 0,
    sliderMax: Int = 100
): Int {
    return ((minPrice + (sliderValue - sliderMin).toDouble() * (maxPrice - minPrice) / (sliderMax - sliderMin)).roundToInt())
}

@Preview
@Composable
fun IsakaroRangeSliderPreview() {
    IsakaroRangeSlider()
}