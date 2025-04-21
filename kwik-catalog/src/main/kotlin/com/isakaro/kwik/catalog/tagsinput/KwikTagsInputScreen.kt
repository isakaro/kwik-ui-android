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
import com.isakaro.kwik.ui.inputfields.KwikTagsInput
import com.isakaro.kwik.ui.inputfields.KwikTagsInputItem
import com.isakaro.kwik.ui.theme.KwikTheme
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
                KwikTagsInputItem("6", "Fountain of Youth")
            )
        }

        ShowCase(
            title = "Tags input with quantity",
        ) {
            var currentTags by remember { mutableStateOf<List<KwikTagsInputItem>>(emptyList()) }

            KwikTagsInput(
                items = sampleTags,
                placeholder = "Enter or select your destination",
                withQuantity = true,
                onTagsChanged = { newTags ->
                    currentTags = newTags
                }
            )
        }

        ShowCase(
            title = "Tags input with initial values",
        ) {
            val initialTags = remember {
                listOf(
                    sampleTags.first(),
                    sampleTags.random()
                )
            }

            var currentTags by remember { mutableStateOf(initialTags) }

            KwikTagsInput(
                items = sampleTags,
                placeholder = "Enter or select your destination",
                initialValues = initialTags,
                onTagsChanged = { newTags ->
                    currentTags = newTags
                }
            )
        }

        ShowCase(
            title = "Tags input with persistent suggestions"
        ) {
            var currentTags by remember { mutableStateOf<List<KwikTagsInputItem>>(emptyList()) }

            KwikTagsInput(
                items = sampleTags,
                placeholder = "Enter or select your destination",
                suggestionsAlwaysVisible = true,
                onTagsChanged = { newTags ->
                    currentTags = newTags
                }
            )
        }

        ShowCase(
            title = "Tags input with outline style"
        ) {
            var currentTags by remember { mutableStateOf<List<KwikTagsInputItem>>(emptyList()) }

            KwikTagsInput(
                items = sampleTags,
                placeholder = "Enter or select your destination",
                outlined = true,
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
