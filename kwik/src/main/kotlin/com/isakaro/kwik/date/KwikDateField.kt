package com.isakaro.kwik.date

import android.view.KeyEvent.KEYCODE_DEL
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isakaro.kwik.R
import com.isakaro.kwik.button.KwikIconButton
import com.isakaro.kwik.inputfields.AllowedChars
import com.isakaro.kwik.inputfields.EmptyTextToolbar
import com.isakaro.kwik.inputfields.kwikTextFieldColors
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.utils.toFormat
import java.time.LocalDate

enum class KwikDatePickerMode {
    Display,
    Edit
}

/**
 * Date field that supports picking a date from a date picker dialog or editing the date manually.
 *
 * @param modifier: The modifier for the date field.
 * @param label: The label for the date field.
 * @param placeholder: The placeholder text for the date field.
 * @param displayFormat: The format to display the date in.
 * @param selected: The callback that is called when a date is selected. Returns in format: yyyy-MM-dd
 * @param border: The border for the date field.
 * @param shape: The shape for the date field.
 * @param mode: The mode for the date field. Defaults to picker.
 * @param leadingIcon: The leading icon for the date field.
 * @param trailingIcon: The trailing icon for the date field.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateField(
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    displayFormat: String = "d MMM yyyy",
    selected: (LocalDate) -> Unit,
    border: BorderStroke? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    mode: KwikDatePickerMode = KwikDatePickerMode.Display,
    onDateParseError: (Exception) -> Unit = {},
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
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val fieldMode = remember { mutableStateOf(mode) }
    var dateDisplay by remember { mutableStateOf(placeholder) }

    if (showDatePicker) {
        KwikDatePickerDialog(
            onDateSelected = {
                selectedDate = it
                selected(it)
                showDatePicker = false
                dateDisplay = it.toFormat("d MMM yyyy")
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Column {
        if(!label.isNullOrBlank()){
            KwikText.TitleMedium(
                modifier = Modifier.padding(bottom = 4.dp),
                text = label,
                color = if(isSystemInDarkTheme()) Color.Gray else Color.DarkGray,
                textAlign = TextAlign.Start
            )
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(fieldMode.value == KwikDatePickerMode.Edit){
                KwikDateField(
                    value = selectedDate,
                    onValidDate = {
                        selected(it)
                    }
                )
            } else {
                Button(
                    modifier = Modifier
                        .weight(1f)
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
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 6.dp),
                icon = if(fieldMode.value == KwikDatePickerMode.Edit) Icons.Default.Close else Icons.Default.Create,
                containerColor = Color.Transparent,
                tint = MaterialTheme.colorScheme.onSurface
            ) {
                if(fieldMode.value == KwikDatePickerMode.Edit){
                    fieldMode.value = KwikDatePickerMode.Display
                } else {
                    fieldMode.value = KwikDatePickerMode.Edit
                }
            }
        }
    }
}

@Composable
fun KwikDateField(
    modifier: Modifier = Modifier,
    value: LocalDate? = null,
    onValidDate: (LocalDate) -> Unit,
    isError: Boolean = false,
    error: String = "",
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = kwikTextFieldColors(),
    onKeyboardDone: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val year = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(value?.year.toString()))
    }
    val month = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(value?.monthValue.toString()))
    }
    val day = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(value?.dayOfMonth.toString()))
    }

    fun isValidDate(y: Int, m: Int, d: Int): Boolean {
        return try {
            LocalDate.of(y, m, d)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun validateAndReturnDate(): Boolean {
        val yr = year.value.text
        val mo = month.value.text
        val dy = day.value.text
        val isValid = yr.length == 4 && mo.length == 2 && dy.length == 2 &&
                try {
                    isValidDate(yr.toInt(), mo.toInt(), dy.toInt())
                    true
                } catch (e: Exception) {
                    false
                }
        if (isValid) {
            onValidDate(LocalDate.of(yr.toInt(), mo.toInt(), dy.toInt()))
        }
        return isValid
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KwikDateDigitField(
                placeholder = "YYYY",
                value = year,
                maxLength = 4,
                isError = isError,
                onValueChange = {
                    year.value = it
                    validateAndReturnDate()
                    try {
                        if (it.text.length == 4) focusManager.moveFocus(FocusDirection.Right)
                    } catch (e: Exception){ }
                },
                onKeyboardDone = onKeyboardDone,
                shape = shape,
                colors = colors
            )
            Spacer(modifier = Modifier.width(8.dp))
            KwikDateDigitField(
                placeholder = "MM",
                value = month,
                maxLength = 2,
                isError = isError,
                onValueChange = {
                    month.value = it
                    validateAndReturnDate()
                    try {
                        if (it.text.length == 2) focusManager.moveFocus(FocusDirection.Right)
                    } catch (e: Exception){ }
                },
                onDelete = {
                    if(month.value.text.isEmpty()) {
                        try {
                            focusManager.moveFocus(FocusDirection.Left)
                        } catch (e: Exception){}
                    }
                },
                onKeyboardDone = onKeyboardDone,
                shape = shape,
                colors = colors
            )
            Spacer(modifier = Modifier.width(8.dp))
            KwikDateDigitField(
                placeholder = "DD",
                value = day,
                maxLength = 2,
                isError = isError,
                onValueChange = {
                    day.value = it
                    validateAndReturnDate()
                },
                onDelete = {
                    if(day.value.text.isEmpty()) {
                        try {
                            focusManager.moveFocus(FocusDirection.Left)
                        } catch (e: Exception){}
                    }
                },
                onKeyboardDone = onKeyboardDone,
                shape = shape,
                colors = colors
            )
        }
        if (isError) {
            KwikText.LabelMedium(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
private fun KwikDateDigitField(
    placeholder: String,
    value: MutableState<TextFieldValue>,
    maxLength: Int,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit,
    shape: Shape,
    colors: TextFieldColors,
    onDelete: () -> Unit = {}
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent
    )

    CompositionLocalProvider(
        LocalTextToolbar provides EmptyTextToolbar,
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        TextField(
            value = value.value,
            onValueChange = {
                val sanitized = AllowedChars.NUMBERS.replace(it.text, "").take(maxLength)
                onValueChange(TextFieldValue(sanitized, selection = TextRange(sanitized.length)))
            },
            placeholder = {
                KwikText.LabelMedium(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            ),
            shape = shape,
            isError = isError,
            colors = colors,
            singleLine = true,
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .width(80.dp)
                .onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KEYCODE_DEL) {
                        onDelete()
                    }
                    false
                }
        )
    }
}


