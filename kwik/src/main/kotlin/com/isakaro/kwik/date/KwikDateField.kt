package com.isakaro.kwik.date

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateField(

) {
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        KwikDatePickerDialog(
            onDateSelected = { selectedDate = it },
            onDismiss = { showDatePicker = false }
        )
    }


}