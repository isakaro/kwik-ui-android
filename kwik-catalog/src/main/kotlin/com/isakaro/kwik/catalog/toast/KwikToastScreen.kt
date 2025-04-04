package com.isakaro.kwik.catalog.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikButton
import com.isakaro.kwik.KwikToast
import com.isakaro.kwik.KwikToastType
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.rememberKwikToastState
import com.isakaro.kwik.showToast
import com.isakaro.kwik.theme.KwikColorError
import com.isakaro.kwik.theme.KwikColorWarning
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
@Destination
internal fun KwikToastScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val scope = rememberCoroutineScope()
    val kwikToastState = rememberKwikToastState()

    KwikToast(state = kwikToastState)

    ScrollableShowCaseContainer(
        title = "Toast",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "KwikToast") {
            KwikButton(text = "Show toast") {
                scope.launch {
                    kwikToastState.showToast("This is a Kwik toast!")
                }
            }
        }
        ShowCase(title = "Warning Toast") {
            KwikButton(
                text = "Show warning toast",
                containerColor = KwikColorWarning
            ) {
                scope.launch {
                    kwikToastState.showToast(
                        message = "This is a warning Kwik toast!",
                        type = KwikToastType.WARNING
                    )
                }
            }
        }
        ShowCase(title = "Error Toast with 6 seconds duration") {
            KwikButton(
                text = "Show long error toast",
                containerColor = KwikColorError
            ) {
                scope.launch {
                    kwikToastState.showToast(
                        message = "This is a Kwik toast with duration",
                        type = KwikToastType.ERROR,
                        duration = 6000L // 6 seconds,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStartScreen() {
    KwikTheme {
        KwikToastScreen()
    }
}
