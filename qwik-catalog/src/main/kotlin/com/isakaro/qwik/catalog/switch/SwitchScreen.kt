package com.isakaro.qwik.catalog.switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikSwitch
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.navigator
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun SwitchScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Switch button",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
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
private fun PreviewStartScreen() {
    QwikTheme {
        SwitchScreen()
    }
}
