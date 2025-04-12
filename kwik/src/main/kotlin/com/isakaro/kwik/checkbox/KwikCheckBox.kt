package com.isakaro.kwik.checkbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
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
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.theme.KwikTheme

@Composable
fun KwikCheckBox(
    checked: Boolean,
    text: String? = null,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colorScheme.primary,
        uncheckedColor = Color.Gray,
        checkmarkColor = Color.White,
        disabledUncheckedColor = Color.White
    ),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            colors = colors,
            onCheckedChange = { isChecked ->
                onCheckedChange(isChecked)
            }
        )
        if(text != null){
            KwikText.TitleSmall(
                modifier = Modifier.clickable {
                    onCheckedChange(!checked)
                },
                text = text,
                color = contentColor
            )
        }
    }
}

@Composable
fun KwikTriStateCheckBox(
    state: ToggleableState? = null,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colorScheme.primary,
        uncheckedColor = Color.Gray,
        checkmarkColor = Color.White,
        disabledUncheckedColor = Color.White
    ),
    onCheckedChange: (ToggleableState) -> Unit,
){
    var counter by remember { mutableIntStateOf(0) }
    val triState = remember(counter) {
        state ?: when (counter % 3) {
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

    TriStateCheckbox(
        state = triState,
        colors = colors,
        onClick = { counter++ }
    )
}

@Preview
@Composable
private fun KwikCheckboxPreview() {
    KwikTheme {
        KwikCheckBox(
            checked = true,
            onCheckedChange = {},
            text = "Checkbox"
        )
    }
}

@Preview
@Composable
private fun KwikTriStateCheckboxPreview() {
    val (state, onStateChange) = remember { mutableStateOf(true) }
    val parentState = remember(state) { if (state) ToggleableState. On else if (!state) ToggleableState. Off else ToggleableState. Indeterminate }

    KwikTheme {
        KwikTriStateCheckBox(
            onCheckedChange = {},
        )
    }
}