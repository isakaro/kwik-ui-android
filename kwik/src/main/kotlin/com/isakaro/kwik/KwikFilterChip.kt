package com.isakaro.kwik

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Data class for toggle group options
 *
 * @param label The label to display for the option
 * @param value The value of the option
 * */
data class KwikFilterChipOption<T>(
    val label: String,
    val value: T
)

/**
 * A filter chip component
 *
 * @param filters List of filter options to display
 * @param selected The currently selected option
 * @param filtersUpdated Callback when the filter options are updated
 * */
@Composable
fun <T> KwikFilterChips(
    filters: List<KwikFilterChipOption<T>>,
    selected: KwikFilterChipOption<T>,
    filtersUpdated: (List<KwikFilterChipOption<T>>) -> Unit,
    multiSelection: Boolean = false
) {
    var selectedOption by remember { mutableStateOf(selected) }
    val selectedOptions = rememberSaveable { mutableSetOf<KwikFilterChipOption<T>>() }

    fun handleSelection(
        onFiltersUpdated: (List<KwikFilterChipOption<T>>) -> Unit,
        option: KwikFilterChipOption<T>,
        isSelected: Boolean
    ) {
        if(multiSelection){
            if (isSelected) {
                selectedOptions.remove(option)
            } else {
                selectedOptions.add(option)
            }
        } else {
            selectedOption = option
            selectedOptions.clear()
            selectedOptions.add(option)
        }

        onFiltersUpdated(selectedOptions.toList())
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters.size) { index ->
            val option = filters[index]

            KwikFilterChip(
                text = option.label,
                isSelected = filters.contains(filters[index]),
                onClick = { isSelected ->
                    handleSelection(
                        option = option,
                        isSelected = isSelected,
                        onFiltersUpdated = filtersUpdated
                    )
                }
            )
        }
    }
}

/**
 * A filter chip component
 *
 * @param text String text to be displayed
 * @param isSelected Boolean if the chip is selected or not
 * @param onClick () -> Unit called when the chip is clicked
 * */
@Composable
fun KwikFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
    onLongPress: (Boolean) -> Unit = {}
) {
    FilterChip(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress(!isSelected) },
                    onTap = { onClick(!isSelected) }
                )
            },
        selected = isSelected,
        onClick = {
            onClick(!isSelected)
        },
        label = {
            Text(
                text = text,
                fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if(isSelected) Color.White else Color.Black
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.White
        ),
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "checked",
                    tint = Color.White,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else null
    )
}

@Composable
fun KwikChip(
    text: String,
    onClick: () -> Unit
) {
    FilterChip(
        selected = false,
        onClick = {
            onClick()
        },
        label = {
            KwikText.TitleSmall(
                text = text,
                color = Color.Black
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.White
        )
    )
}