package com.isakaro.kwik.catalog.tagsinput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.textfield.KwikTagsInput
import com.isakaro.kwik.textfield.KwikTagsInputItem
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
fun KwikTagsInputScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ScrollableShowCaseContainer(
        title = "Tags input",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        val sampleTags = remember {
            listOf(
                KwikTagsInputItem("1", "Tortuga"),
                KwikTagsInputItem("2", "Shipwreck Cove"),
                KwikTagsInputItem("3", "Davy Jones' Locker"),
                KwikTagsInputItem("4", "Port Royal"),
                KwikTagsInputItem("5", "Isla de Muerta"),
                KwikTagsInputItem("6", "Singapore")
            )
        }

        val initialTags = remember {
            listOf(sampleTags.first())
        }

        var currentTags by remember { mutableStateOf(initialTags) }

        ShowCase {
            KwikTagsInput(
                items = sampleTags,
                placeholder = "Enter or select your destination",
                initialValues = initialTags,
                withQuantity = true,
                onTagsChanged = { newTags ->
                    currentTags = newTags
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikTagsInputScreen() {
    KwikTheme {
        KwikTagsInputScreen()
    }
}
