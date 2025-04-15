package com.isakaro.kwik.date

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.isakaro.kwik.textfield.KwikOutlinedTextField
import com.isakaro.kwik.textfield.KwikTextField
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateField(
    outlined: Boolean = false,
    placeholder: String = "Select date",
    selected: (Date) -> Unit,
) {
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val dateValue = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(
        TextFieldValue("")
    ) }

    if (showDatePicker) {
        KwikDatePickerDialog(
            onDateSelected = {
                selectedDate = it
                selected(it)
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if(outlined){
        KwikOutlinedTextField(
            value = dateValue,
            onValueChange = { dateValue.value = it },
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    } else {
        KwikTextField(
            value = dateValue,
            onValueChange = { dateValue.value = it },
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}