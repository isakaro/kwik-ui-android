package com.isakaro.ui.catalog.switch

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.ui.catalog.common.ShowCase
import com.isakaro.ui.catalog.common.ShowCaseContainer
import app.isakaro.ui.library.theme.Theme.AmpersandTheme

@Composable
internal fun SwitchScreen() {
    ShowCaseContainer {
        ShowCase(title = "Switch") {
            val checkedState = remember { mutableStateOf(true) }
            Switch(
                colors = SwitchDefaults.colors().copy(
                    checkedThumbColor = Color.Black
                ),
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        SwitchScreen()
    }
}
