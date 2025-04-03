package com.isakaro.Kwik.catalog.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.Kwik.KwikButton
import com.isakaro.Kwik.KwikToast
import com.isakaro.Kwik.KwikToastType
import com.isakaro.Kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.Kwik.catalog.ShowCase
import com.isakaro.Kwik.navigator
import com.isakaro.Kwik.rememberKwikToastState
import com.isakaro.Kwik.showToast
import com.isakaro.Kwik.theme.KwikColorError
import com.isakaro.Kwik.theme.KwikColorWarning
import com.isakaro.Kwik.theme.Theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
@Destination
internal fun KwikToastScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val scope = rememberCoroutineScope()
    val KwikToastState = rememberKwikToastState()

    KwikToast(state = KwikToastState)

    ScrollableShowCaseContainer(
        title = "Toast",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "KwikToast") {
            KwikButton(text = "Show toast") {
                scope.launch {
                    KwikToastState.showToast("This is a Kwik toast!")
                }
            }
        }
        ShowCase(title = "Warning Toast") {
            KwikButton(
                text = "Show warning toast",
                containerColor = KwikColorWarning
            ) {
                scope.launch {
                    KwikToastState.showToast(
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
                    KwikToastState.showToast(
                        message = "This is a Kwik toast with duration",
                        type = KwikToastType.ERROR,
                        duration = 6000L // 6 seconds
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
