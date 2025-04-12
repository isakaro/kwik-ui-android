package com.isakaro.kwik.date

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.R
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.utils.toMMdd
import java.util.Date

/**
 * A date field with a label and a date. When clicked, a date picker dialog will be shown.
 *
 * @param label: The label for the date field.
 * @param onDateRangeSelected: Callback that is called when a date range is selected. The Pair contains the start and end dates.
 * @param minSelectableDate: The minimum selectable date in milliseconds since epoch. If null, 99 years before today is used.
 * @param maxSelectableDate: The maximum selectable date in milliseconds since epoch. If null, 99 years from today is used.
 * @param showModeToggle: Whether to show the mode toggle button.
 * @param dialogShape: The shape of the date picker dialog.
 * @param colors: The colors to use for the date picker.
 * @param onClick: Callback that is called when the date field is clicked.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateRangeButton(
    modifier: Modifier = Modifier,
    label: String,
    onDateRangeSelected: (Pair<Date, Date>) -> Unit,
    minSelectableDate: Long? = null,
    maxSelectableDate: Long? = null,
    showModeToggle: Boolean = false,
    dialogShape: Shape = MaterialTheme.shapes.medium,
    colors: DatePickerColors = DatePickerDefaults.colors().copy(
        containerColor = MaterialTheme.colorScheme.surface,
        selectedDayContainerColor = MaterialTheme.colorScheme.primary,
        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.secondary,
        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onSurface,
        selectedYearContainerColor = MaterialTheme.colorScheme.primary,
        disabledDayContentColor = Color.Gray
    ),
    onClick: () -> Unit = {}
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var dateDisplay by remember { mutableStateOf("Select dates") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        KwikText.TitleMedium(
            text = label,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            modifier = modifier
                .height(50.dp),
            onClick = {
                onClick()
                showDatePicker = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, Color.Gray),
            contentPadding = PaddingValues(4.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null,
                )

                KwikText.BodyLarge(
                    modifier = Modifier.align(Alignment.Center),
                    text = dateDisplay
                )

                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.arrow_down),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null,
                )
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
                    dateDisplay  = "${selectedDates.first.toMMdd()} - ${selectedDates.second.toMMdd()}"
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