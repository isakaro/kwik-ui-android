package com.isakaro.kwik.ui.date

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.button.KwikTextButton
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.utils.toLocalDate
import com.isakaro.kwik.ui.utils.toMillis
import java.time.LocalDate

/**
 * A date range picker dialog that allows the user to select a date range.
 *
 * @param title: The title of the dialog.
 * @param confirmText: The text for the confirm button.
 * @param cancelText: The text for the cancel button.
 * @param minSelectableDate: The minimum selectable date in milliseconds since epoch. If null, 99 years before today is used.
 * @param maxSelectableDate: The maximum selectable date in milliseconds since epoch. If null, 99 years from today is used.
 * @param onDateRangeSelected: Callback that is called when a date range is selected. The Pair contains the start and end dates.
 * @param showModeToggle: Whether to show the mode toggle button.
 * @param colors: The colors to use for the date picker.
 * @param onDismiss: Callback that is called when the dialog is dismissed.
 *
 * * @see [KwikDateRangeButton] for a date field that shows this dialog when clicked.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateRangePickerDialog(
    title: String = "Select date range",
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    minSelectableDate: Long? = null,
    maxSelectableDate: Long? = null,
    onDateRangeSelected: (Pair<LocalDate, LocalDate>) -> Unit,
    showModeToggle: Boolean = false,
    colors: DatePickerColors = kwikDatePickerColors(),
    shape: Shape = MaterialTheme.shapes.medium,
    onDismiss: () -> Unit
) {
    val today = LocalDate.now()
    val ninetyNineYearsBefore = today.minusYears(99)
    val ninetyNineYearsLater = today.plusYears(99)

    val minSelectable = minSelectableDate ?: ninetyNineYearsBefore.toMillis()
    val maxSelectable = maxSelectableDate ?: ninetyNineYearsLater.toMillis()

    val dateRangePickerState = rememberDateRangePickerState(
        selectableDates = object: SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in minSelectable..maxSelectable
            }
        }
    )

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
                    val startMillis = dateRangePickerState.selectedStartDateMillis
                    val endMillis = dateRangePickerState.selectedEndDateMillis

                    if (startMillis != null && endMillis != null) {
                        val startDate = startMillis.toLocalDate()
                        val endDate = endMillis.toLocalDate()
                        onDateRangeSelected(Pair(startDate, endDate))
                    }
                    onDismiss()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun kwikDatePickerColors(): DatePickerColors {
    return DatePickerDefaults.colors().copy(
        containerColor = MaterialTheme.colorScheme.surface,
        selectedDayContainerColor = MaterialTheme.colorScheme.primary,
        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.secondary,
        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onSurface,
        selectedYearContainerColor = MaterialTheme.colorScheme.primary,
        disabledDayContentColor = Color.Gray
    )
}