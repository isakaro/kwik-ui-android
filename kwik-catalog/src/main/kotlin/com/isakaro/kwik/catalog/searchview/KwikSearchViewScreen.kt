package com.isakaro.kwik.catalog.searchview

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.inputfields.KwikSearchView
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.toast.KwikToast
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.toast.rememberKwikToastState
import com.isakaro.kwik.ui.toast.showToast
import com.isakaro.kwik.ui.theme.KwikTheme
import com.isakaro.kwik.ui.utils.text
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikSearchViewScreen(
    navigator: DestinationsNavigator = navigator()
) {

    val kwikToastState = rememberKwikToastState()

    KwikToast(state = kwikToastState)

    ScrollableShowCaseContainer(
        title = "Kwik search view",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Search view") {
            val query = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikSearchView(
                state = query,
                placeholder = "Enter search query...",
                onTextChange = { query ->

                },
                onTextCleared = {
                    kwikToastState.showToast("Text cleared")
                }
            )

            KwikVSpacer(12)

            KwikText.BodyMedium(
                text = buildAnnotatedString {
                    append("Search query: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(query.text)
                    }
                }
            )
        }

        ShowCase(title = "Search view with suggestions") {
            val query = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikSearchView(
                suggestionsModifier = Modifier.padding(horizontal = 8.dp),
                state = query,
                placeholder = "Enter address...",
                onTextChange = { query ->

                },
                suggestions = listOf("Tortuga", "Isla de Muerta", "Shipwreck Cove", "Davy Jones' Locker"),
                onTextCleared = {}
            )

            KwikVSpacer(12)

            KwikText.BodyMedium(
                text = buildAnnotatedString {
                    append("Search query: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(query.text)
                    }
                }
            )
        }

        ShowCase(title = "Search view with label") {
            val query = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikSearchView(
                label = "Address",
                suggestionsModifier = Modifier.padding(horizontal = 8.dp),
                state = query,
                placeholder = "Enter address...",
                onTextChange = { query ->

                },
                suggestions = listOf("Tortuga", "Isla de Muerta", "Shipwreck Cove", "Davy Jones' Locker"),
                onTextCleared = {}
            )

            KwikVSpacer(12)

            KwikText.BodyMedium(
                text = buildAnnotatedString {
                    append("Search query: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(query.text)
                    }
                }
            )
        }

        ShowCase(title = "Search view with delay (debounce - 2 seconds)") {
            val state = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            var debouncedQuery by remember { mutableStateOf("") }

            KwikSearchView(
                state = state,
                placeholder = "Enter search query...",
                delay = true,
                delayDuration = 2000L,
                onTextChange = { query ->
                    debouncedQuery = query
                }
            )

            KwikVSpacer(12)

            KwikText.BodyMedium(
                text = buildAnnotatedString {
                    append("Search query: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(debouncedQuery)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikSearchViewScreen() {
    KwikTheme {
        KwikSearchViewScreen()
    }
}
