package com.isakaro.qwik.catalog.daterangepicker

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikDateField
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.theme.Theme.QwikTheme

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun QwikDateRangePickerScreen() {
    ShowCaseContainer {
        ShowCase(title = "Date Range Picker") {
            QwikDateField(
                label = "Check-in and check-out",
                onDateRangeSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    QwikTheme {
        QwikDateRangePickerScreen()
    }
}