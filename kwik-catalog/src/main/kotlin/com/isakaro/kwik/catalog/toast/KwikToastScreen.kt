package com.isakaro.kwik.catalog.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.toast.KwikToast
import com.isakaro.kwik.ui.toast.KwikToastType
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.toast.rememberKwikToastState
import com.isakaro.kwik.ui.toast.showToast
import com.isakaro.kwik.ui.theme.KwikColorError
import com.isakaro.kwik.ui.theme.KwikColorWarning
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
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
private fun PreviewToastScreen() {
    KwikTheme {
        KwikToastScreen()
    }
}
