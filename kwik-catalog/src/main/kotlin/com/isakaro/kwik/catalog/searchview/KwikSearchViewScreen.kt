package com.isakaro.kwik.catalog.searchview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikSearchView
import com.isakaro.kwik.KwikText
import com.isakaro.kwik.KwikToast
import com.isakaro.kwik.KwikVSpacer
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.rememberKwikToastState
import com.isakaro.kwik.showToast
import com.isakaro.kwik.theme.KwikTheme
import com.isakaro.kwik.utils.text
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

        ShowCase(title = "Search view suggestions") {
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

        ShowCase(title = "Search view with delay (debounce)") {
            val query = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikSearchView(
                state = query,
                placeholder = "Enter search query...",
                delay = true,
                delayDuration = 2000L, // 2 second delay
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
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikSearchViewScreen() {
    KwikTheme {
        KwikSearchViewScreen()
    }
}
