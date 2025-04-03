package com.isakaro.qwik.catalog.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikButton
import com.isakaro.qwik.QwikToast
import com.isakaro.qwik.QwikToastType
import com.isakaro.qwik.catalog.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.navigator
import com.isakaro.qwik.rememberQwikToastState
import com.isakaro.qwik.showToast
import com.isakaro.qwik.theme.QwikColorError
import com.isakaro.qwik.theme.QwikColorWarning
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
@Destination
internal fun QwikToastScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val scope = rememberCoroutineScope()
    val qwikToastState = rememberQwikToastState()

    QwikToast(state = qwikToastState)

    ScrollableShowCaseContainer(
        title = "Toast",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "QwikToast") {
            QwikButton(text = "Show toast") {
                scope.launch {
                    qwikToastState.showToast("This is a Qwik toast!")
                }
            }
        }
        ShowCase(title = "Warning Toast") {
            QwikButton(
                text = "Show warning toast",
                containerColor = QwikColorWarning
            ) {
                scope.launch {
                    qwikToastState.showToast(
                        message = "This is a warning Qwik toast!",
                        type = QwikToastType.WARNING
                    )
                }
            }
        }
        ShowCase(title = "Error Toast with 6 seconds duration") {
            QwikButton(
                text = "Show long error toast",
                containerColor = QwikColorError
            ) {
                scope.launch {
                    qwikToastState.showToast(
                        message = "This is a Qwik toast with duration",
                        type = QwikToastType.ERROR,
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
    QwikTheme {
        QwikToastScreen()
    }
}
