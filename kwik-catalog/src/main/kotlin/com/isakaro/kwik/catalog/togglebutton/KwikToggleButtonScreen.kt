package com.isakaro.kwik.catalog.togglebutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikText
import com.isakaro.kwik.KwikToggleGroup
import com.isakaro.kwik.KwikToggleGroupOption
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikToggleButtonScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Toggle button",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Toggle button") {
            val (value, onValueChange) = remember { mutableStateOf("") }

            KwikToggleGroup(
                options = listOf(
                    KwikToggleGroupOption("Tortuga", "Tortuga"),
                    KwikToggleGroupOption("Singapore", "Singapore"),
                    KwikToggleGroupOption("Port Royal", "Port Royal")
                ),
                selectedOption = 2,
                onOptionSelected = {
                    onValueChange(it.toString())
                }
            )

            KwikText.BodyMedium(
                text = "Selected option: $value"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikToggleButtonScreen() {
    KwikTheme {
        KwikToggleButtonScreen()
    }
}
