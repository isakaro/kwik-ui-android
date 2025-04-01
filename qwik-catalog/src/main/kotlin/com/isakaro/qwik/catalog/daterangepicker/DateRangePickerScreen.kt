package com.isakaro.qwik.catalog.daterangepicker

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikDateField
import com.isakaro.qwik.QwikDateRangePickerDialog
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.theme.Theme.QwikTheme

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun QwikDateRangePickerScreen() {

    var showDatePicker by remember { mutableStateOf(false) }
    var dates by remember { mutableStateOf("Select dates") }

    ShowCaseContainer {
        ShowCase(title = "Date Range Picker") {
            QwikDateField(
                label = "Check-in and check-out",
                date = dates,
            ) {
                showDatePicker = true
            }

            if (showDatePicker) {
                QwikDateRangePickerDialog(
                    onDateRangeSelected = { selectedDates ->
                        dates  = "${selectedDates.first} - ${selectedDates.second}"
                    },
                    onDismiss = {
                        showDatePicker = false
                    }
                )
            }
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