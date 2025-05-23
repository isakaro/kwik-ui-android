package com.isakaro.kwik.catalog.togglebutton

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.ui.button.KwikToggleGroup
import com.isakaro.kwik.ui.button.KwikToggleGroupOption
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.ui.text.KwikText
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
                    KwikToggleGroupOption("Fountain of Youth", "Fountain of Youth"),
                    KwikToggleGroupOption("Port Royal", "Port Royal")
                ),
                selectedOption = 2,
                onOptionSelected = {
                    onValueChange(it.toString())
                }
            )

            KwikVSpacer(12)

            KwikText.BodyMedium(
                text = "Selected option: $value"
            )
        }

        ShowCase(title = "Toggle button") {
            val (value, onValueChange) = remember { mutableStateOf("") }

            KwikToggleGroup(
                modifier = Modifier.height(45.dp),
                options = listOf(
                    KwikToggleGroupOption("Dark", "Dark"),
                    KwikToggleGroupOption("Light", "Light"),
                ),
                selectedOption = 2,
                onOptionSelected = {
                    onValueChange(it.toString())
                }
            )

            KwikVSpacer(12)

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
