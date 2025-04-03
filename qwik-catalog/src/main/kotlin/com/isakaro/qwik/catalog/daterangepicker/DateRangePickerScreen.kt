package com.isakaro.qwik.catalog.daterangepicker

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikDateField
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.navigator
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun QwikDateRangePickerScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Date range picker",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
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