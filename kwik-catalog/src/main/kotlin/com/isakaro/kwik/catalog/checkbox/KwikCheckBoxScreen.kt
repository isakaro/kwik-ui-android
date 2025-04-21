package com.isakaro.kwik.catalog.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.checkbox.KwikCheckBox
import com.isakaro.kwik.ui.checkbox.KwikTriStateCheckBox
import com.isakaro.kwik.ui.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikCheckBoxScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Checkbox",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "CheckBox") {
            var checked by remember { mutableStateOf(true) }

            KwikCheckBox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
        }

        ShowCase(title = "Tristate CheckBox") {
            var state by remember { mutableStateOf(ToggleableState.Off) }

            KwikTriStateCheckBox(
                state = state,
                onCheckedChange = {
                    state = it
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikCheckBoxScreen() {
    KwikTheme {
        KwikCheckBoxScreen()
    }
}
