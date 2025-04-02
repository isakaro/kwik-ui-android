package com.isakaro.qwik.catalog.switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikSwitch
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.theme.Theme.QwikTheme

@Composable
internal fun SwitchScreen() {
    ShowCaseContainer {
        ShowCase(title = "Switch") {
            val checkedState = remember { mutableStateOf(true) }

            QwikSwitch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    QwikTheme {
        SwitchScreen()
    }
}
