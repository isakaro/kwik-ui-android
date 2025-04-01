package com.isakaro.qwik

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
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
import com.isakaro.qwik.theme.ColorPrimaryAccent
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QwikDateRangePickerModal(
    title: String = "Select date range",
    onDateRangeSelected: (Pair<LocalDate, LocalDate>) -> Unit,
    onDismiss: () -> Unit
) {
    val today = LocalDate.now()
    val sixMonthsLater = today.plusMonths(6)

    val todayMillis = today.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    val sixMonthsLaterMillis = sixMonthsLater.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

    var checkInDate by remember { mutableStateOf<LocalDate?>(null) }
    var checkOutDate by remember { mutableStateOf<LocalDate?>(null) }

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
            containerColor = Color.White,
        ),
        confirmButton = {
            TextButton(
                onClick = {
                    val first = dateRangePickerState.selectedStartDateMillis.toLocalDate()
                    val second = dateRangePickerState.selectedEndDateMillis.toLocalDate()

                    if (first != null && second != null) {
                        onDateRangeSelected(Pair(first, second))
                        checkInDate = first
                        checkOutDate = second
                    }
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = ColorPrimaryAccent
                )
            ) {
                Text(
                    text = "Confirm",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray,
                )
            },
            colors = DatePickerDefaults.colors().copy(
                containerColor = Color.White,
                selectedDayContainerColor = ColorPrimaryAccent,
                dayInSelectionRangeContainerColor = ColorPrimaryAccentLight,
                dayInSelectionRangeContentColor = Color.White,
                selectedYearContainerColor = ColorPrimaryAccent,
                disabledDayContentColor = Color.Gray
            ),
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(12.dp)
        )
    }
}