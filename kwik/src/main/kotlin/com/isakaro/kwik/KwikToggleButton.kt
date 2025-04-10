package com.isakaro.kwik

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.theme.KwikTheme

@Composable
fun <T> KwikToggleGroup(
    options: List<KwikToggleGroupOption<T>>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        val index = options.indexOfFirst { it.value == selectedOption }
        if(index >= 0){
            selectedIndex = index
        }
    }

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, item ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedIndex = index
                    onOptionSelected(item.value)
                },
                selected = index == selectedIndex,
                colors = SegmentedButtonDefaults.colors().copy(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                label = {
                    KwikText.LabelMedium(
                        text = item.label,
                        fontWeight = if(index == selectedIndex) FontWeight.Bold else null,
                        maxLines = 2,
                        color = if(index == selectedIndex) Color.White else Color.Gray
                    )
                }
            )
        }
    }
}

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