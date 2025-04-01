package app.isakaro.ui.library

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ToggleGroup(
    options: List<QwikToggleGroupOption<T>>,
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
                    Text(
                        text = item.label,
                        fontWeight = if(index == selectedIndex) FontWeight.Bold else null,
                        color = if(index == selectedIndex) Color.White else Color.Gray
                    )
                }
            )
        }
    }
}

data class QwikToggleGroupOption<T>(
    val label: String,
    val value: T
)

@Preview
@Composable
fun IsakaroToggleGroupPreview() {
    ToggleGroup(
        options = listOf(
            QwikToggleGroupOption("Option 1", 1),
            QwikToggleGroupOption("Option 2", 2),
            QwikToggleGroupOption("Option 3", 3),
        ),
        selectedOption = 2,
        onOptionSelected = {}
    )
}