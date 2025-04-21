package com.isakaro.kwik.ui.date

import android.view.KeyEvent.KEYCODE_DEL
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import com.isakaro.R
import com.isakaro.kwik.ui.button.KwikIconButton
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.inputfields.AllowedChars
import com.isakaro.kwik.ui.inputfields.EmptyTextToolbar
import com.isakaro.kwik.ui.inputfields.kwikTextFieldColors
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.utils.blankIfNull
import com.isakaro.kwik.ui.utils.toFormat
import java.time.LocalDate

enum class KwikDatePickerMode {
    Hybrid,
    Picker,
    Input
}

/**
 * A hybrid date field that supports picking a date from a date picker dialog or editing the date manually.
 *
 * @param modifier: The modifier for the date field.
 * @param label: The label for the date field.
 * @param placeholder: The placeholder text for the date field.
 * @param displayFormat: The format to display the date in.
 * @param selected: The callback that is called when a date is selected. Returns in format: yyyy-MM-dd
 * @param border: The border for the date field.
 * @param shape: The shape for the date field.
 * @param mode: The mode for the date field. Defaults to HYBRID, allowing for manual editing or date picker selection.
 * @param leadingIcon: The leading icon for the date field.
 * @param trailingIcon: The trailing icon for the date field.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikDateFieldButton(
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    displayFormat: String = "d MMM yyyy",
    selected: (LocalDate) -> Unit,
    border: BorderStroke? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    mode: KwikDatePickerMode = KwikDatePickerMode.Hybrid,
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
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val fieldMode = remember { mutableStateOf(if(mode == KwikDatePickerMode.Hybrid) KwikDatePickerMode.Picker else mode) }
    var dateDisplay by remember { mutableStateOf(placeholder) }

    if (showDatePicker) {
        KwikDatePickerDialog(
            onDateSelected = {
                selectedDate = it
                selected(it)
                showDatePicker = false
                dateDisplay = it.toFormat(displayFormat)
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            when(fieldMode.value){
                KwikDatePickerMode.Picker -> {
                    Button(
                        modifier = modifier
                            .weight(1f)
                            .heightIn(55.dp),
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
                KwikDatePickerMode.Input -> {
                    KwikDateField(
                        modifier = modifier,
                        value = selectedDate,
                        onValidDate = {
                            selectedDate = it
                            selected(it)
                            dateDisplay = it.toFormat(displayFormat)
                        }
                    )
                }
                else -> {}
            }
            if(mode == KwikDatePickerMode.Hybrid){
                KwikIconButton(
                    modifier = Modifier
                        .size(35.dp)
                        .padding(horizontal = 2.dp),
                    icon = {
                        KwikImageView(
                            modifier = Modifier.fillMaxSize().padding(4.dp),
                            url = if(fieldMode.value == KwikDatePickerMode.Input) R.drawable.calendar else Icons.Default.Create,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    containerColor = Color.Transparent
                ) {
                    if(fieldMode.value == KwikDatePickerMode.Input){
                        fieldMode.value = KwikDatePickerMode.Picker
                    } else {
                        fieldMode.value = KwikDatePickerMode.Input
                    }
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
        mutableStateOf(TextFieldValue(value?.year.toString().blankIfNull()))
    }
    val month = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(value?.monthValue.toString().blankIfNull()))
    }
    val day = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(value?.dayOfMonth.toString().blankIfNull()))
    }
    var yearError by remember { mutableStateOf(false) }
    var monthError by remember { mutableStateOf(false) }
    var dayError by remember { mutableStateOf(false) }
    var yearErrorText by remember { mutableStateOf("") }
    var monthErrorText by remember { mutableStateOf("") }
    var dayErrorText by remember { mutableStateOf("") }

    fun validatedAndSubmit(y: Int, m: Int, d: Int): Boolean {
        return try {
            val date = LocalDate.of(y, m, d)
            onValidDate(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun validateYear(year: String): Boolean {
        val valid = year.length == 4 && year.toIntOrNull() != null
        yearError = !valid && year.isNotEmpty()
        if (yearError) yearErrorText = "Invalid year"
        return valid
    }

    fun validateMonth(month: String): Boolean {
        val valid = month.isNotEmpty() &&
                month.toIntOrNull() != null &&
                month.toInt() in 1..12
        monthError = !valid && month.isNotEmpty()
        if (monthError) monthErrorText = "Invalid month"
        return valid
    }

    fun validateDay(day: String): Boolean {
        val valid = day.isNotEmpty() &&
                day.toIntOrNull() != null &&
                day.toInt() in 1..31
        dayError = !valid && day.isNotEmpty()
        if (dayError) dayErrorText = "Invalid day"
        return valid
    }

    fun validateAndSubmitDate() {
        val validYear = validateYear(year.value.text)
        val validMonth = validateMonth(month.value.text)
        val validDay = validateDay(day.value.text)

        if (validYear && validMonth && validDay) {
            try {
                if (validatedAndSubmit(year.value.text.toInt(), month.value.text.toInt(), day.value.text.toInt())) {
                    yearError = false
                    monthError = false
                    dayError = false
                }
            } catch (e: Exception) {}
        }
    }

    Column(modifier = modifier) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Start
        ) {
            KwikDateDigitField(
                placeholder = "YYYY",
                value = year,
                maxLength = 4,
                isError = yearError,
                onValueChange = {
                    year.value = it
                    if (it.text.isNotEmpty()) {
                        if (validateYear(it.text)) {
                            try {
                                focusManager.moveFocus(FocusDirection.Right)
                            } catch (e: Exception){}
                        }
                        validateAndSubmitDate()
                    } else {
                        yearError = false
                    }
                },
                onKeyboardDone = {
                    validateAndSubmitDate()
                    onKeyboardDone()
                },
                shape = shape,
                colors = colors
            )
            Spacer(modifier = Modifier.width(8.dp))
            KwikDateDigitField(
                placeholder = "MM",
                value = month,
                maxLength = 2,
                isError = monthError,
                onValueChange = {
                    month.value = it
                    if (it.text.isNotEmpty()) {
                        if (validateMonth(it.text) && it.text.isNotEmpty()) {
                            if (it.text.length == 2 || it.text.toInt() > 1) {
                                try {
                                    focusManager.moveFocus(FocusDirection.Right)
                                } catch (e: Exception){}
                            }
                        }
                        validateAndSubmitDate()
                    } else {
                        monthError = false
                    }
                },
                onDelete = {
                    if(month.value.text.isEmpty()) {
                        try {
                            focusManager.moveFocus(FocusDirection.Left)
                        } catch (e: Exception){}
                    }
                },
                onKeyboardDone = {
                    if (validateMonth(month.value.text)) {
                        try {
                            focusManager.moveFocus(FocusDirection.Right)
                        } catch (e: Exception){}
                        validateAndSubmitDate()
                    }
                    onKeyboardDone()
                },
                shape = shape,
                colors = colors
            )
            Spacer(modifier = Modifier.width(8.dp))
            KwikDateDigitField(
                placeholder = "DD",
                value = day,
                maxLength = 2,
                isError = dayError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    day.value = it
                    if (it.text.isNotEmpty()) {
                        validateDay(it.text)
                        validateAndSubmitDate()
                    } else {
                        dayError = false
                    }
                },
                onDelete = {
                    if(day.value.text.isEmpty()) {
                        try {
                            focusManager.moveFocus(FocusDirection.Left)
                        } catch (e: Exception){}
                    }
                },
                onKeyboardDone = {
                    validateAndSubmitDate()
                    onKeyboardDone()
                },
                shape = shape,
                colors = colors
            )
        }
        if (yearError) {
            KwikText.LabelMedium(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = yearErrorText,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start
            )
        }
        if (monthError) {
            KwikText.LabelMedium(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = monthErrorText,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start
            )
        }
        if (dayError) {
            KwikText.LabelMedium(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = dayErrorText,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    ),
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
            label = {
                KwikText.LabelMedium(
                    modifier = Modifier.fillMaxWidth(),
                    text = placeholder,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            keyboardOptions = keyboardOptions,
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