package com.isakaro.kwik.button

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.isakaro.kwik.card.KwikCard
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.theme.KwikTheme

/**
 * A toggle group component
 *
 * @param options List of options to display
 * @param selectedOption The currently selected option
 * @param onOptionSelected Callback when an option is selected
 * @param elevation Card elevation
 *
 * Usage:
 *
 * ```
 * KwikToggleGroup(
 *     options = listOf(
 *          KwikToggleGroupOption("Option 1", 1),
 *          KwikToggleGroupOption("Option 2", 2),
 *          KwikToggleGroupOption("Option 3", 3)
 *     ),
 *     selectedOption = 2,
 *     onOptionSelected = { selected ->
 *          // Handle option selection
 *     }
 * )
 * ```
 *
 * @see [KwikToggleGroupOption] for the options data class
 * */
@Composable
fun <T> KwikToggleGroup(
    modifier: Modifier = Modifier,
    options: List<KwikToggleGroupOption<T>>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    shape: Shape = MaterialTheme.shapes.medium
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val buttonDimens = remember { mutableStateListOf<Pair<Int, Int>>().apply {
        repeat(options.size) { add(Pair(0, 0)) }
    }}
    val indicatorOffset = remember { Animatable(0f) }

    LaunchedEffect(selectedOption) {
        val index = options.indexOfFirst { it.value == selectedOption }
        if(index >= 0){
            selectedIndex = index
        }
    }

    LaunchedEffect(selectedIndex) {
        val targetOffset = buttonDimens.take(selectedIndex).sumOf { it.first }
        indicatorOffset.animateTo(
            targetValue = targetOffset.toFloat(),
            animationSpec = spring(
                dampingRatio = 0.8f,
                stiffness = 300f
            )
        )
    }

    KwikCard(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        elevation = elevation,
        shape = shape
    ) {
        Box {
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .padding(horizontal = 6.dp)
                    .offset { IntOffset(indicatorOffset.value.toInt(), 0) }
                    .width(with(LocalDensity.current) { buttonDimens.getOrNull(selectedIndex)?.first?.toDp() ?: 0.dp })
                    .clip(shape)
                    .height(with(LocalDensity.current) { buttonDimens.getOrNull(selectedIndex)?.second?.toDp() ?: 0.dp })
                    .background(MaterialTheme.colorScheme.primary)
                    .zIndex(0f)
            )

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                options.forEachIndexed { index, item ->
                    SegmentedButton(
                        modifier = Modifier.padding(2.dp).onGloballyPositioned {
                            buttonDimens[index] = Pair(it.size.width, it.size.height * 4/5)
                        },
                        shape = MaterialTheme.shapes.medium,
                        onClick = {
                            selectedIndex = index
                            onOptionSelected(item.value)
                        },
                        border = BorderStroke(0.dp, Color.Transparent),
                        icon = {
                            null
                        },
                        selected = index == selectedIndex,
                        colors = SegmentedButtonDefaults.colors().copy(
                            activeContainerColor = Color.Transparent,
                            inactiveContainerColor = Color.Transparent,
                            activeContentColor = Color.Transparent
                        ),
                        label = {
                            KwikText.LabelMedium(
                                modifier = Modifier.animateContentSize(),
                                text = item.label,
                                fontWeight = if (index == selectedIndex) FontWeight.Bold else null,
                                maxLines = 2,
                                color = if (index == selectedIndex) Color.White else Color.Gray
                            )
                        }
                    )
                }
            }
        }
    }
}

/**
 * Data class for toggle group options
 *
 * @param label The label to display for the option
 * @param value The value of the option
 * */
data class KwikToggleGroupOption<T>(
    val label: String,
    val value: T
)

@Preview
@Composable
private fun KwikToggleGroupPreview() {
    KwikTheme {
        KwikToggleGroup(
            options = listOf(
                KwikToggleGroupOption("Option 1", 1),
                KwikToggleGroupOption("Option 2", 2),
                KwikToggleGroupOption("Option 3", 3),
            ),
            selectedOption = 2,
            onOptionSelected = {}
        )
    }
}