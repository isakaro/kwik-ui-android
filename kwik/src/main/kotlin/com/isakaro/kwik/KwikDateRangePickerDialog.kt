package com.isakaro.kwik

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date

/**
 * A date range picker dialog that allows the user to select a date range.
 *
 * * @param title: The title of the dialog.
 * * @param confirmText: The text for the confirm button.
 * * @param cancelText: The text for the cancel button.
 * * @param onDateRangeSelected: Callback that is called when a date range is selected. The Pair contains the start and end dates.
 * * @param showModeToggle: Whether to show the mode toggle button.
 * * @param colors: The colors to use for the date picker.
 * * @param onDismiss: Callback that is called when the dialog is dismissed.
 *
 * * @see [KwikDateField] for a date field that shows this dialog when clicked.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateRangePickerDialog(
    title: String = "Select date range",
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    onDateRangeSelected: (Pair<Date, Date>) -> Unit,
    showModeToggle: Boolean = false,
    colors: DatePickerColors,
    onDismiss: () -> Unit
) {
    val calendar = Calendar.getInstance()

    val today = calendar.clone() as Calendar

    val sixMonthsLater = calendar.clone() as Calendar
    sixMonthsLater.add(Calendar.MONTH, 6)

    val todayMillis = today.timeInMillis
    val sixMonthsLaterMillis = sixMonthsLater.timeInMillis

    var checkInDate by remember { mutableStateOf<Date?>(null) }
    var checkOutDate by remember { mutableStateOf<Date?>(null) }

    val dateRangePickerState = rememberDateRangePickerState(
        selectableDates = object: SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in todayMillis..sixMonthsLaterMillis
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        confirmButton = {
            TextButton(
                onClick = {
                    val startMillis = dateRangePickerState.selectedStartDateMillis
                    val endMillis = dateRangePickerState.selectedEndDateMillis

                    if (startMillis != null && endMillis != null) {
                        val startDate = Date(startMillis)
                        val endDate = Date(endMillis)

                        onDateRangeSelected(Pair(startDate, endDate))
                        checkInDate = startDate
                        checkOutDate = endDate
                    }
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = confirmText,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(cancelText)
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                KwikText.TitleMedium(
                    text = title
                )
            },
            colors = colors,
            showModeToggle = showModeToggle,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(12.dp)
        )
    }
}