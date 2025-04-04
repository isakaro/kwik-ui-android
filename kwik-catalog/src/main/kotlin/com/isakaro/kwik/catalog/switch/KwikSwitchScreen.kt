package com.isakaro.kwik.catalog.switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikSwitch
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun KwikSwitchScreen(
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

            KwikSwitch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikScreen() {
    KwikTheme {
        KwikSwitchScreen()
    }
}
