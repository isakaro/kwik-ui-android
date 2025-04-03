package com.isakaro.qwik.catalog.radio

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikRadioButtonGroup
import com.isakaro.qwik.QwikRadioItem
import com.isakaro.qwik.QwikText
import com.isakaro.qwik.QwikVSpacer
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.navigator
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun RadioButtonScreen(
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
                QwikRadioItem("Tortuga", 141),
                QwikRadioItem("Isla de Muerta", 141),
                QwikRadioItem("Davy Jones' Locker", 141),
                QwikRadioItem("Shipwreck Cove", 141),
            )

            val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }

            Column {
                QwikRadioButtonGroup(
                    options = options,
                    initialSelectedValue = selectedOption.value,
                    onOptionSelected = {
                        onOptionSelected(it)
                    }
                )

                QwikVSpacer(12)

                QwikText.TitleText(
                    text = "Selected option: ${selectedOption.label}"
                )
            }
        }

        ShowCase(title = "Radio Button") {
            val options = listOf(
                QwikRadioItem("Captain Jack Sparrow", 141),
                QwikRadioItem("Captain Hector Barbossa", 141),
                QwikRadioItem("Calypso", 141),
                QwikRadioItem("Davy Jones", 141),
            )

            val (selectedOption, onOptionSelected) = remember { mutableStateOf<QwikRadioItem<Int>?>(null) }

            Column {
                QwikRadioButtonGroup(
                    options = options,
                    onOptionSelected = {
                        onOptionSelected(it)
                    }
                )

                QwikVSpacer(12)

                if(selectedOption != null){
                    QwikText.TitleText(
                        text = "Selected option: ${selectedOption.label}"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    QwikTheme {
        RadioButtonScreen()
    }
}
