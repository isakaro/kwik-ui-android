package com.isakaro.kwik.date

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.R
import com.isakaro.kwik.button.KwikIconButton
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.textfield.KwikOutlinedTextField
import com.isakaro.kwik.textfield.KwikTextField
import com.isakaro.kwik.utils.toFormat
import java.util.Date

enum class KwikDatePickerMode {
    Display,
    Edit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateField(
    modifier: Modifier = Modifier,
    outlined: Boolean = false,
    label: String = "Date",
    placeholder: String = "",
    displayFormat: String = "d MMM yyyy",
    selected: (Date) -> Unit,
    border: BorderStroke? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    leadingIcon: @Composable (() -> Unit?) = {
        Icon(
            painter = painterResource(id = R.drawable.calendar),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null,
        )
    },
    trailingIcon: @Composable (() -> Unit?) = {
        Icon(
            painter = painterResource(id = R.drawable.arrow_down),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null,
        )
    }
) {
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val dateValue = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(
        TextFieldValue("")
    ) }
    val mode = remember { mutableStateOf(KwikDatePickerMode.Display) }
    var dateDisplay by remember { mutableStateOf(placeholder) }

    if (showDatePicker) {
        KwikDatePickerDialog(
            onDateSelected = {
                selectedDate = it
                selected(it)
                showDatePicker = false
                dateDisplay  = it.toFormat(displayFormat)
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(mode.value == KwikDatePickerMode.Edit){
            if(outlined){
                KwikOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(selectedDate) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showDatePicker = true
                                }
                            }
                        },
                    value = dateValue,
                    onValueChange = { dateValue.value = it },
                    placeholder = placeholder,
                    singleLine = true
                )
            } else {
                KwikTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(selectedDate) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showDatePicker = true
                                }
                            }
                        },
                    value = dateValue,
                    label = label,
                    onValueChange = { dateValue.value = it },
                    placeholder = placeholder,
                    singleLine = true
                )
            }
        } else {
            Button(
                modifier = Modifier
                    .then(modifier),
                onClick = {
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
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
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
        KwikIconButton(
            modifier = Modifier.size(45.dp),
            icon = if(mode.value == KwikDatePickerMode.Edit) Icons.Default.Create else Icons.Default.Close,
            tint = Color.Black,
        ) {
            if(mode.value == KwikDatePickerMode.Edit){
                mode.value = KwikDatePickerMode.Display
            } else {
                dateValue.value = TextFieldValue("")
                selectedDate = null
                selected(Date(0))
                mode.value = KwikDatePickerMode.Edit
            }
        }
    }
}