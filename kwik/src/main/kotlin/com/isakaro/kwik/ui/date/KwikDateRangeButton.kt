package com.isakaro.kwik.ui.date

import android.R.attr.label
import android.R.attr.onClick
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.R
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.utils.toFormat
import java.time.LocalDate

/**
 * A date button with a label and a date. When clicked, a date range picker dialog will be shown.
 *
 * @param label: The label for the date field.
 * @param onDateRangeSelected: Callback that is called when a date range is selected. The Pair contains the start and end dates.
 * @param minSelectableDate: The minimum selectable date in milliseconds since epoch. If null, 99 years before today is used.
 * @param maxSelectableDate: The maximum selectable date in milliseconds since epoch. If null, 99 years from today is used.
 * @param showModeToggle: Whether to show the mode toggle button.
 * @param dialogShape: The shape of the date picker dialog.
 * @param colors: The colors to use for the date picker.
 * @param onClick: Callback that is called when the date field is clicked.
 * @param border: The border to use for the date field.
 * @param shape: The shape of the date field.
 * @param leadingIcon: The leading icon to use for the date field.
 * @param trailingIcon: The trailing icon to use for the date field.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateRangeButton(
    modifier: Modifier = Modifier,
    label: String? = null,
    onDateRangeSelected: (Pair<LocalDate, LocalDate>) -> Unit,
    minSelectableDate: Long? = null,
    maxSelectableDate: Long? = null,
    showModeToggle: Boolean = false,
    dialogShape: Shape = MaterialTheme.shapes.medium,
    colors: DatePickerColors = kwikDatePickerColors(),
    onClick: () -> Unit = {},
    border: BorderStroke? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    datePlaceholder: String = "Select dates",
    leadingIcon: @Composable (() -> Unit?) = {
        Icon(
            painter = painterResource(id = R.drawable.calendar),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null,
        )
    },
    trailingIcon: @Composable (() -> Unit?) = {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null,
        )
    }
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var dateDisplay by remember { mutableStateOf(datePlaceholder) }

    Column {
        if(!label.isNullOrBlank()){
            KwikText.TitleMedium(
                modifier = Modifier.padding(bottom = 4.dp),
                text = label,
                color = if(isSystemInDarkTheme()) Color.Gray else Color.DarkGray,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = modifier
                    .weight(1f)
                    .heightIn(55.dp),
                onClick = {
                    onClick()
                    showDatePicker = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = border,
                shape = shape
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    leadingIcon()

                    KwikText.BodyMedium(
                        text = dateDisplay
                    )

                    trailingIcon()
                }
            }
        }

        if (showDatePicker) {
            KwikDateRangePickerDialog(
                colors = colors,
                minSelectableDate = minSelectableDate,
                maxSelectableDate = maxSelectableDate,
                showModeToggle = showModeToggle,
                shape = dialogShape,
                onDateRangeSelected = { selectedDates ->
                    onDateRangeSelected(selectedDates)
                    dateDisplay  = "${selectedDates.first.toFormat()} - ${selectedDates.second.toFormat()}"
                },
                onDismiss = {
                    showDatePicker = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun KwikDateRangeButtonPreview() {
    KwikDateRangeButton(
        label = "Check-in",
        onDateRangeSelected = { },
        onClick = {}
    )
}