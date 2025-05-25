package com.isakaro.kwik.ui.radio

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
import com.isakaro.kwik.ui.text.KwikText
import java.io.Serializable

@Composable
fun <T> ColumnScope.KwikRadioButtonGroup(
    options: List<KwikRadioItem<T>>,
    initialSelectedValue: T? = null,
    onOptionSelected: (T) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }

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
                KwikText.BodyMedium(
                    text = item.label,
                    color = if (selectedIndex == index) MaterialTheme.colorScheme.onSurface else Color.Gray
                )
            }
        }
    }
}

data class KwikRadioItem<T>(
    val label: String,
    val value: T
): Serializable

@Preview
@Composable
private fun KwikRadioButtonGroupPreview() {
    val colorOptions = listOf(
        KwikRadioItem("Tortuga", 404),
        KwikRadioItem("Isla de Muerta", 300),
        KwikRadioItem("Shipwreck Cove", 141)
    )

    Column {
        KwikRadioButtonGroup(
            options = colorOptions,
            initialSelectedValue = 123,
            onOptionSelected = {

            }
        )
    }
}