package com.isakaro.qwik.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.isakaro.qwik.theme.ColorPrimaryAccent

@Composable
fun IsakaroSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    Switch(
        checked = checked,
        onCheckedChange = { enabled ->
            onCheckedChange(enabled)
        },
        colors = SwitchDefaults.colors(
            checkedTrackColor = ColorPrimaryAccent,
            checkedThumbColor = Color.White,
            uncheckedThumbColor = Color.Gray,
            uncheckedTrackColor = Color.LightGray
        )
    )
}