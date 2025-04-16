package com.isakaro.kwik.date

import androidx.compose.foundation.BorderStroke
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
import java.util.Calendar
import java.util.Date

private enum class KwikDatePickerMode {
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
            KwikDateField(
                modifier = Modifier.weight(1f),
                onValidDate = {

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
            modifier = Modifier.size(40.dp).padding(horizontal = 6.dp),
            icon = if(mode.value == KwikDatePickerMode.Edit) Icons.Default.Close else Icons.Default.Create,
            containerColor = Color.Transparent,
            tint = MaterialTheme.colorScheme.onSurface
        ) {
            if(mode.value == KwikDatePickerMode.Edit){
                mode.value = KwikDatePickerMode.Display
            } else {
                mode.value = KwikDatePickerMode.Edit
            }
        }
    }
}

@Composable
fun KwikDateField(
    modifier: Modifier = Modifier,
    onValidDate: (String) -> Unit,
    isError: Boolean = false,
    error: String = "",
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = kwikTextFieldColors(),
    onKeyboardDone: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val year = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val month = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val day = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    fun isValidDate(y: Int, m: Int, d: Int): Boolean {
        return try {
            val calendar = Calendar.getInstance().apply {
                isLenient = false
                set(Calendar.YEAR, y)
                set(Calendar.MONTH, m - 1)
                set(Calendar.DAY_OF_MONTH, d)
                time
            }
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
            onValidDate("$year-$month-$day")
        }
        return isValid
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DateDigitField(
                label = "YYYY",
                value = year,
                maxLength = 4,
                isError = isError,
                onValueChange = {
                    year.value = it
                    validateAndReturnDate()
                    if (it.text.length == 4) focusManager.moveFocus(FocusDirection.Right)
                },
                onKeyboardDone = onKeyboardDone,
                shape = shape,
                colors = colors
            )
            Spacer(modifier = Modifier.width(8.dp))
            DateDigitField(
                label = "MM",
                value = month,
                maxLength = 2,
                isError = isError,
                onValueChange = {
                    month.value = it
                    validateAndReturnDate()
                    if (it.text.length == 2) focusManager.moveFocus(FocusDirection.Right)
                },
                onKeyboardDone = onKeyboardDone,
                shape = shape,
                colors = colors
            )
            Spacer(modifier = Modifier.width(8.dp))
            DateDigitField(
                label = "DD",
                value = day,
                maxLength = 2,
                isError = isError,
                onValueChange = {
                    day.value = it
                    validateAndReturnDate()
                },
                onKeyboardDone = onKeyboardDone,
                shape = shape,
                colors = colors
            )
        }
        if (isError) {
            KwikText.LabelMedium(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
private fun DateDigitField(
    label: String,
    value: MutableState<TextFieldValue>,
    maxLength: Int,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit,
    shape: Shape,
    colors: TextFieldColors
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
                    text = label
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
            modifier = Modifier.width(80.dp)
        )
    }
}


