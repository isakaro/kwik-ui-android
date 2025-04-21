package com.isakaro.kwik.ui.switch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Simple Material3 switch with text support
 *
 * @param checked
 * @param onCheckedChange
 * */
@Composable
fun KwikSwitch(
    text: @Composable (() -> Unit)? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 6.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = checked,
            onCheckedChange = { enabled ->
                onCheckedChange(enabled)
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.LightGray
            )
        )
        text?.invoke()
    }
}