package com.isakaro.kwik.ui.button

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.card.KwikCard
import com.isakaro.kwik.ui.text.KwikText
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
    shape: Shape = MaterialTheme.shapes.medium,
    selectedOptionColor: Color = MaterialTheme.colorScheme.primary,
    selectedOptionShape: Shape = MaterialTheme.shapes.small,
    selectedOptionTextColor: Color = Color.White,
    unselectedOptionTextColor: Color = Color.Gray,
    containerColor: Color = MaterialTheme.colorScheme.surface,
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val buttonOffsets = remember { mutableStateListOf<Offset>().apply { repeat(options.size){ add(Offset.Zero) } } }
    val buttonSizes = remember { mutableStateListOf<IntSize>().apply { repeat(options.size){ add(IntSize.Zero) } } }

    LaunchedEffect(selectedOption) {
        val index = options.indexOfFirst { it.value == selectedOption }
        selectedIndex = if (index >= 0) {
            index
        } else {
            0
        }
    }

    val animatedOffsetX = remember { Animatable(0f) }
    val animatedOffsetY = remember { Animatable(0f) }
    val animatedWidth = remember { Animatable(0f) }
    val animatedHeight = remember { Animatable(0f) }

    LaunchedEffect(selectedIndex) {
        val targetOffset = buttonOffsets[selectedIndex]
        val targetSize = buttonSizes[selectedIndex]

        launch {
            animatedOffsetX.animateTo(
                targetValue = targetOffset.x,
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
            )
        }

        launch {
            animatedOffsetY.animateTo(
                targetValue = targetOffset.y,
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
            )
        }

        launch {
            animatedWidth.animateTo(
                targetValue = targetSize.width.toFloat(),
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
            )
        }

        launch {
            animatedHeight.animateTo(
                targetValue = targetSize.height.toFloat(),
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
            )
        }
    }

    KwikCard(
        containerColor = containerColor,
        elevation = elevation,
        shape = shape
    ) {
        Box {
            if (buttonOffsets.isNotEmpty() && buttonSizes.isNotEmpty() && buttonOffsets.size > selectedIndex && buttonSizes.size > selectedIndex) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .offset {
                            IntOffset(
                                x = animatedOffsetX.value.roundToInt(),
                                y = animatedOffsetY.value.roundToInt()
                            )
                        }
                        .width(with(LocalDensity.current) { animatedWidth.value.toDp() })
                        .height(with(LocalDensity.current) { animatedHeight.value.toDp() })
                        .clip(selectedOptionShape)
                        .background(selectedOptionColor)
                )
            }

            SingleChoiceSegmentedButtonRow(
                modifier = modifier
                    .padding(4.dp)
                    .heightIn(30.dp)
            ) {
                options.forEachIndexed { index, item ->
                    SegmentedButton(
                        modifier = Modifier
                            .onGloballyPositioned {
                                buttonOffsets[index] = it.positionInParent()
                                buttonSizes[index] = it.size
                            },
                        shape = MaterialTheme.shapes.extraSmall,
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
                            KwikText.LabelSmall(
                                modifier = Modifier.animateContentSize(),
                                text = item.label,
                                textAlign = TextAlign.Center,
                                fontWeight = if (index == selectedIndex) FontWeight.Bold else null,
                                maxLines = 2,
                                color = if (index == selectedIndex) selectedOptionTextColor else unselectedOptionTextColor
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