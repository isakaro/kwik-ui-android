package com.isakaro.kwik.ui.filterchip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.text.KwikText
import java.io.Serializable
import java.util.UUID

/**
 * Data class for toggle group options
 *
 * @param label The label to display for the option
 * @param value The value of the option
 * */
data class KwikFilterChipOption<T>(
    val label: String,
    val value: T,
    val id: UUID = UUID.randomUUID()
): Serializable

/**
 * A filter chip component with multi-selection support
 *
 * @param filters List of filter options to display
 * @param preSelection Set of pre-selected options
 * @param filtersUpdated Callback when filter options are updated
 * @param multiSelection Boolean if multiple options can be selected
 * @param selectedContainerColor Color of the chip when selected
 * @param unselectedContainerColor Color of the chip when unselected
 * @param selectedContentColor Color of the text when selected
 * @param unselectedContentColor Color of the text when unselected
 * @param border BorderStroke? border stroke of the chip
 * @param shape Shape shape of the chip
 * @param showCheckedIcon Boolean if the checked icon should be shown
 * @param flowLayout Boolean if the chips should be displayed in a flow layout
 * @param flowLayoutVerticalArrangement Int vertical arrangement of the flow layout
 * @param flowLayoutHorizontalArrangement Int horizontal arrangement of the flow layout
 * */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> KwikFilterChips(
    filters: List<KwikFilterChipOption<T>>,
    preSelection: Set<T> = mutableSetOf(),
    filtersUpdated: (List<T>) -> Unit,
    multiSelection: Boolean = false,
    selectedContainerColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContainerColor: Color = if(isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
    selectedContentColor: Color = Color.White,
    unselectedContentColor: Color = if(isSystemInDarkTheme()) Color.White else Color.Black,
    border: BorderStroke = BorderStroke(
        width = 0.dp,
        color = Color.Transparent
    ),
    shape: Shape = MaterialTheme.shapes.medium,
    showCheckedIcon: Boolean = false,
    flowLayout: Boolean = false,
    flowLayoutVerticalArrangement: Int = 0,
    flowLayoutHorizontalArrangement: Int = 2
) {
    val selectedOptions = rememberSaveable {
        mutableStateOf(preSelection)
    }

    val listState = rememberLazyListState()

    fun handleSelection(
        onFiltersUpdated: (List<T>) -> Unit,
        option: T,
        isSelected: Boolean
    ) {
        if(multiSelection){
            if(selectedOptions.value.contains(option)){
                selectedOptions.value -= option
            } else {
                selectedOptions.value += option
            }
        } else {
            if(!selectedOptions.value.contains(option)){
                selectedOptions.value = setOf(option)
            }
        }

        onFiltersUpdated(selectedOptions.value.toList())
    }

    if(flowLayout){
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = flowLayoutHorizontalArrangement.dp
            ),
            verticalArrangement = Arrangement.spacedBy(
                space = flowLayoutVerticalArrangement.dp
            )
        ) {
            filters.forEach { option ->
                KwikFilterChip(
                    text = option.label,
                    isSelected = selectedOptions.value.contains(option.value),
                    onClick = { isSelected ->
                        handleSelection(
                            option = option.value,
                            isSelected = isSelected,
                            onFiltersUpdated = filtersUpdated
                        )
                    },
                    selectedContainerColor = selectedContainerColor,
                    unselectedContainerColor = unselectedContainerColor,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor,
                    border = border,
                    shape = shape,
                    showCheckedIcon = showCheckedIcon
                )
            }
        }
    } else {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters.size) { index ->
                val option = filters[index]

                KwikFilterChip(
                    text = option.label,
                    isSelected = selectedOptions.value.contains(option.value),
                    onClick = { isSelected ->
                        handleSelection(
                            option = option.value,
                            isSelected = isSelected,
                            onFiltersUpdated = filtersUpdated
                        )
                    },
                    selectedContainerColor = selectedContainerColor,
                    unselectedContainerColor = unselectedContainerColor,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor,
                    border = border,
                    shape = shape,
                    showCheckedIcon = showCheckedIcon
                )
            }
        }
    }
}

/**
 * A filter chip component
 *
 * @param text String text to be displayed
 * @param isSelected Boolean if the chip is selected or not
 * @param onClick () -> Unit called when the chip is clicked
 * @param onLongPress () -> Unit called when the chip is long pressed
 * @param selectedContainerColor Color of the chip when selected
 * @param unselectedContainerColor Color of the chip when unselected
 * @param selectedContentColor Color of the text when selected
 * @param unselectedContentColor Color of the text when unselected
 * @param border BorderStroke? border stroke of the chip
 * @param shape Shape shape of the chip
 * */
@Composable
private fun KwikFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
    onLongPress: (Boolean) -> Unit = {},
    selectedContainerColor: Color,
    unselectedContainerColor: Color,
    selectedContentColor: Color,
    unselectedContentColor: Color,
    border: BorderStroke? = null,
    shape: Shape,
    showCheckedIcon: Boolean = true
) {
    KwikChip(
        text = text,
        isSelected = isSelected,
        onClick = { onClick(isSelected) },
        onLongPress = { onLongPress(isSelected) },
        selectedContainerColor = selectedContainerColor,
        unselectedContainerColor = unselectedContainerColor,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        border = border,
        shape = shape
    ) {
        if (isSelected && showCheckedIcon) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = selectedContentColor
            )
        }
    }
}

/**
 * A chip component
 *
 * @param text String text to be displayed
 * @param isSelected Boolean if the chip is selected or not
 * @param onClick () -> Unit called when the chip is clicked
 * @param onLongPress () -> Unit called when the chip is long pressed
 * @param selectedContainerColor Color of the chip when selected
 * @param unselectedContainerColor Color of the chip when unselected
 * @param selectedContentColor Color of the text when selected
 * @param unselectedContentColor Color of the text when unselected
 * @param border BorderStroke? border stroke of the chip
 * @param shape Shape shape of the chip
 * */
@Composable
fun KwikChip(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    onLongPress: (Boolean) -> Unit = {},
    selectedContainerColor: Color = MaterialTheme.colorScheme.surface,
    unselectedContainerColor: Color = if(isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
    selectedContentColor: Color = Color.White,
    unselectedContentColor: Color = if(isSystemInDarkTheme()) Color.White else Color.Black,
    border: BorderStroke? = null,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    leadingIcon: @Composable (() -> Unit?)
) {
    FilterChip(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress(!isSelected) },
                    onTap = { onClick() }
                )
            },
        selected = isSelected,
        onClick = {
            onClick()
        },
        shape = shape,
        border = if(!isSelected) border else null,
        label = {
            KwikText.TitleSmall(
                text = text,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) selectedContentColor else unselectedContentColor
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = selectedContainerColor,
            containerColor = unselectedContainerColor
        ),
        leadingIcon = {
            leadingIcon.invoke()
        }
    )
}

@Preview
@Composable
private fun PreviewKwikFilterChips() {
    val filters = listOf(
        KwikFilterChipOption("Option 1", 1),
        KwikFilterChipOption("Option 2", 2),
        KwikFilterChipOption("Option 3", 3),
        KwikFilterChipOption("Option 4", 4),
        KwikFilterChipOption("Option 5", 5)
    )

    var selected by remember { mutableStateOf(filters.map { it.value }) }

    KwikFilterChips(
        filters = filters,
        preSelection = setOf(filters.map { it.value }.random()),
        filtersUpdated = { selected = it }
    )
}

@Preview
@Composable
private fun PreviewKwikFilterChipsInFlowLayout() {
    val filters = listOf(
        KwikFilterChipOption("Option 1", 1),
        KwikFilterChipOption("Option 2", 2),
        KwikFilterChipOption("Option 3", 3),
        KwikFilterChipOption("Option 4", 4),
        KwikFilterChipOption("Option 5", 5)
    )

    var selected by remember { mutableStateOf(filters.map { it.value }) }

    KwikFilterChips(
        filters = filters,
        flowLayout = true,
        preSelection = setOf(filters.map { it.value }.random()),
        filtersUpdated = { selected = it }
    )
}

