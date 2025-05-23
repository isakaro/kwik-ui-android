package com.isakaro.kwik.ui.switch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.text.KwikText

/**
 * Simple Material3 switch with text support
 *
 * @param checked true if the switch is checked, false otherwise
 * @param text to show next to the switch
 * @param colors [SwitchColors] colors for the switch
 * @param onCheckedChange callback to be invoked when the switch is checked or unchecked
 * */
@Composable
fun KwikSwitch(
    text: String? = null,
    checked: Boolean,
    colors: SwitchColors = kwikSwitchColors(),
    onCheckedChange: (Boolean) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 6.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(text != null){
            KwikText.LabelMedium(
                text = text
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = { enabled ->
                onCheckedChange(enabled)
            },
            colors = colors
        )
    }
}

@Composable
fun kwikSwitchColors(): SwitchColors {
    return SwitchDefaults.colors(
        checkedTrackColor = MaterialTheme.colorScheme.primary,
        checkedThumbColor = Color.White,
        uncheckedThumbColor = Color.Gray,
        uncheckedTrackColor = Color.LightGray,
        uncheckedBorderColor = Color.Transparent
    )
}