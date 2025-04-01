package com.isakaro.ui.catalog.checkbox

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.ui.catalog.common.ShowCase
import com.isakaro.ui.catalog.common.ShowCaseContainer
import app.isakaro.ui.library.theme.PrimaryColor

@Composable
internal fun CheckBoxScreen() {
    ShowCaseContainer {
        ShowCase(title = "CheckBox") {
            var checked by remember { mutableStateOf(true) }
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = PrimaryColor,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.Black
                ),
                checked = checked,
                onCheckedChange = { checked = checked.not() }
            )
        }
        ShowCase(title = "Tristate CheckBox") {
            var counter by remember { mutableStateOf(0) }
            val triState = remember(counter) {
                when (counter % 3) {
                    0 -> ToggleableState.On
                    1 -> ToggleableState.Off
                    else -> ToggleableState.Indeterminate
                }
            }
            TriStateCheckbox(
                state = triState,
                colors = CheckboxDefaults.colors(
                    checkedColor = PrimaryColor,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.Black
                ),
                onClick = { counter++ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    CheckBoxScreen()
}
