package com.isakaro.qwik.catalog.radio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.catalog.common.ShowCase
import com.isakaro.qwik.catalog.common.ShowCaseContainer
import com.isakaro.qwik.theme.Theme.AmpersandTheme

@Composable
internal fun RadioButtonScreen() {
    ShowCaseContainer {
        ShowCase(title = "Radio Button") {
            val radioOptions = listOf("Calls", "Missed", "Friends")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
            // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
            Column(Modifier.selectableGroup()) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = text == selectedOption,
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Black
                            ),
                            selected = text == selectedOption,
                            onClick = null // null recommended for accessibility with screen readers
                        )
                        Text(
                            text = text,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        RadioButtonScreen()
    }
}
