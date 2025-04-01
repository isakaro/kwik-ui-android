package com.isakaro.qwik

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun <T> ColumnScope.QwikOptionsView(
    options: List<QwikRadioItem<T>>,
    initialSelectedValue: T? = null,
    onOptionSelected: (T) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    // Set initial selected index based on provided initial value
    LaunchedEffect(initialSelectedValue) {
        initialSelectedValue?.let { initialValue ->
            val index = options.indexOfFirst { it.value == initialValue }
            if (index >= 0) {
                selectedIndex = index
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth().weight(1f, fill = false)
    ) {
        itemsIndexed(options) { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedIndex = index
                        onOptionSelected(item.value)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedIndex == index,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = Color.Gray
                    ),
                    onClick = {
                        selectedIndex = index
                        onOptionSelected(item.value)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if(selectedIndex == index) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }
    }
}

data class QwikRadioItem<T>(
    val label: String,
    val value: T
)

@Preview
@Composable
fun RadioButtonExample() {
    val colorOptions = listOf(
        QwikRadioItem("Option 1", 123),
        QwikRadioItem("Option 2", 101),
        QwikRadioItem("Option 3", 455)
    )

    Column {
        QwikOptionsView(
            options = colorOptions,
            initialSelectedValue = 123,
            onOptionSelected = {

            }
        )
    }
}
