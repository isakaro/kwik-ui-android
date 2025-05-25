package com.isakaro.kwik.catalog.radio

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.radio.KwikRadioButtonGroup
import com.isakaro.kwik.ui.radio.KwikRadioItem
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikRadioButtonScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Radio button",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Radio Button with preselected option") {
            val options = listOf(
                KwikRadioItem("Tortuga", 141),
                KwikRadioItem("Isla de Muerta", 141),
                KwikRadioItem("Davy Jones' Locker", 141),
                KwikRadioItem("Shipwreck Cove", 141),
            )

            val (selectedOption, onOptionSelected) = remember { mutableIntStateOf(options[0].value) }

            Column {
                KwikRadioButtonGroup(
                    options = options,
                    initialSelectedValue = selectedOption,
                    onOptionSelected = {
                        onOptionSelected(it)
                    }
                )

                KwikVSpacer(12)

                KwikText.TitleMedium(
                    text = "Selected option: ${options.first { it.value == selectedOption }.label}"
                )
            }
        }

        ShowCase(title = "Radio Button") {
            val options = listOf(
                KwikRadioItem("Captain Jack Sparrow", 141),
                KwikRadioItem("Captain Hector Barbossa", 141),
                KwikRadioItem("Calypso", 141),
                KwikRadioItem("Davy Jones", 141),
            )

            val (selectedOption, onOptionSelected) = remember { mutableStateOf<Int?>(null) }

            Column {
                KwikRadioButtonGroup(
                    options = options,
                    onOptionSelected = {
                        onOptionSelected(it)
                    }
                )

                KwikVSpacer(12)

                if(selectedOption != null){
                    KwikText.TitleMedium(
                        text = "Selected option: ${options.first { it.value == selectedOption }.label}"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikRadioButtonScreen() {
    KwikTheme {
        KwikRadioButtonScreen()
    }
}
