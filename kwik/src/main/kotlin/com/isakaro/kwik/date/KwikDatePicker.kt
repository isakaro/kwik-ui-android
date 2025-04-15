package com.isakaro.kwik.date

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.button.KwikButton
import com.isakaro.kwik.button.KwikTextButton
import com.isakaro.kwik.text.KwikText
import java.util.Calendar
import java.util.Date

/**
 * A date picker dialog that allows the user to select a date range.
 *
 * @param title: The title of the dialog.
 * @param confirmText: The text for the confirm button.
 * @param cancelText: The text for the cancel button.
 * @param minSelectableDate: The minimum selectable date in milliseconds since epoch. If null, 99 years before today is used.
 * @param maxSelectableDate: The maximum selectable date in milliseconds since epoch. If null, 99 years from today is used.
 * @param onDateSelected: Callback that is called when a date range is selected. The Pair contains the start and end dates.
 * @param showModeToggle: Whether to show the mode toggle button.
 * @param confirmOnSelection: Whether to confirm the selection on date selection.
 * @param colors: The colors to use for the date picker.
 * @param onDismiss: Callback that is called when the dialog is dismissed.
 *
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDatePickerDialog(
    title: String = "Select date",
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    minSelectableDate: Long? = null,
    maxSelectableDate: Long? = null,
    onDateSelected: (Date) -> Unit,
    showModeToggle: Boolean = false,
    confirmOnSelection: Boolean = true,
    colors: DatePickerColors = kwikDatePickerColors(),
    shape: Shape = MaterialTheme.shapes.medium,
    onDismiss: () -> Unit
) {
    val calendar = Calendar.getInstance()

    val ninetyNineYearsBefore = calendar.clone() as Calendar
    ninetyNineYearsBefore.add(Calendar.YEAR, -99)

    val ninetyNineYearsLater = calendar.clone() as Calendar
    ninetyNineYearsLater.add(Calendar.YEAR, 99)

    val minSelectable = minSelectableDate ?: ninetyNineYearsBefore.timeInMillis
    val maxSelectable = maxSelectableDate ?: ninetyNineYearsLater.timeInMillis

    val datePickerState = rememberDatePickerState(
        selectableDates = object: SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in minSelectable..maxSelectable
            }
        }
    )

    fun dateSelected(){
        datePickerState.selectedDateMillis?.let {
            val date = Date(it)
            onDateSelected(date)
            onDismiss()
        }
    }

    LaunchedEffect(datePickerState) {
        if(confirmOnSelection){
            dateSelected()
        }
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = shape,
        confirmButton = {
            KwikButton(
                text = confirmText,
                onClick = {
                    dateSelected()
                }
            )
        },
        dismissButton = {
            KwikTextButton(
                onClick = onDismiss,
                text = {
                    KwikText.LabelMedium(
                        text = cancelText
                    )
                }
            )
        }
    ) {
        DatePicker(
            state = datePickerState,
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