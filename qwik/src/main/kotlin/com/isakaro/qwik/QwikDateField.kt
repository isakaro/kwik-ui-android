package com.isakaro.qwik

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.utils.toMMdd
import java.util.Date

/**
 * A date field with a label and a date. When clicked, a date picker dialog will be shown.
 * @param label: The label for the date field.
 * @param onDateRangeSelected: Callback that is called when a date range is selected. The Pair contains the start and end dates.
 * @param onClick: Callback that is called when the date field is clicked.
 * */
@Composable
fun QwikDateField(
    modifier: Modifier = Modifier,
    label: String,
    onDateRangeSelected: (Pair<Date, Date>) -> Unit,
    onClick: () -> Unit = {}
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var dateDisplay by remember { mutableStateOf("Select dates") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        Text(
            text = label,
            color = Color.Gray,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
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
                containerColor = Color.White,
            ),
            border = BorderStroke(2.dp, Color.DarkGray),
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
                    tint = Color.DarkGray,
                    contentDescription = null,
                )

                QwikText.BodyText(
                    modifier = Modifier.align(Alignment.Center),
                    text = dateDisplay,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.arrow_down),
                    tint = Color.DarkGray,
                    contentDescription = null,
                )
            }
        }

        if (showDatePicker) {
            QwikDateRangePickerDialog(
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

@Preview
@Composable
fun QwikDateFieldPreview() {
    QwikDateField(
        label = "Check-in",
        onDateRangeSelected = { },
        onClick = {}
    )
}