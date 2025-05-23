package com.isakaro.kwik.catalog.switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.ui.switch.KwikSwitch
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.theme.KwikColorError
import com.isakaro.kwik.ui.theme.KwikColorSuccess
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
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
                text = "Control the lights",
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )

            KwikVSpacer(12)

            if (checkedState.value) {
                KwikText.BodyMedium(
                    text = "The lights are on",
                    color = KwikColorSuccess
                )
            } else {
                KwikText.BodyMedium(
                    text = "The lights are off",
                    color = KwikColorError
                )
            }
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
