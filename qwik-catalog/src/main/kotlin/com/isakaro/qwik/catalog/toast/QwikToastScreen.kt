package com.isakaro.qwik.catalog.toast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.QwikButton
import com.isakaro.qwik.QwikToast
import com.isakaro.qwik.QwikToastType
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.rememberQwikToastState
import com.isakaro.qwik.showToast
import com.isakaro.qwik.theme.Theme.QwikTheme
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
internal fun QwikToastScreen() {
    val scope = rememberCoroutineScope()
    val qwikToastState = rememberQwikToastState()

    val calendar = Calendar.getInstance()
    val tenSeconds = calendar.clone() as Calendar
    tenSeconds.add(Calendar.SECOND, 10)

    QwikToast(state = qwikToastState)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowCase(title = "QwikToast") {
            QwikButton(text = "Show toast") {
                scope.launch {
                    qwikToastState.showToast("This is a Qwik toast!")
                }
            }

            QwikButton(text = "Show warning toast") {
                scope.launch {
                    qwikToastState.showToast(
                        message = "This is a warning Qwik toast!",
                        type = QwikToastType.WARNING
                    )
                }
            }

            QwikButton(text = "Show long error toast") {
                scope.launch {
                    qwikToastState.showToast(
                        message = "This is a long warning Qwik toast!",
                        type = QwikToastType.ERROR,
                        duration = tenSeconds.timeInMillis
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
        QwikToastScreen()
    }
}
