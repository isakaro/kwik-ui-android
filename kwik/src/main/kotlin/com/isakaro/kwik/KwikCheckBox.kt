package com.isakaro.kwik

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun KwikCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { isChecked ->
                onCheckedChange(isChecked)
            }
        )
        KwikText.BodyText(
            modifier = Modifier.clickable {
                onCheckedChange(!checked)
            },
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun KwikTriStateCheckBox(
    state: ToggleableState? = null,
    onCheckedChange: (ToggleableState) -> Unit
){
    var _state = state
    var counter by remember { mutableIntStateOf(0) }
    val triState = remember(counter) {
        if(_state != null) {
            _state = null
            return@remember state
        } else {
            when (counter % 3) {
                0 -> {
                    onCheckedChange(ToggleableState.On)
                    ToggleableState.On
                }
                1 -> {
                    onCheckedChange(ToggleableState.Off)
                    ToggleableState.Off
                }
                else -> {
                    onCheckedChange(ToggleableState.Indeterminate)
                    ToggleableState.Indeterminate
                }
            }
        }
    }
    TriStateCheckbox(
        state = triState!!,
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.primary,
            uncheckedColor = Color.Gray,
            checkmarkColor = Color.White
        ),
        onClick = { counter++ }
    )
}

@Preview
@Composable
private fun KwikCheckboxPreview() {
    KwikCheckBox(
        checked = true,
        onCheckedChange = {},
        text = "Checkbox"
    )
}

@Preview
@Composable
private fun KwikTriStateCheckboxPreview() {
    val (state, onStateChange) = remember { mutableStateOf(true) }
    val parentState = remember(state) { if (state) ToggleableState. On else if (!state) ToggleableState. Off else ToggleableState. Indeterminate }

    KwikTriStateCheckBox(
        onCheckedChange = {},
    )
}