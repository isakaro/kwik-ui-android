package com.isakaro.kwik.date

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.R
import com.isakaro.kwik.button.KwikIconButton
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.inputfields.KwikOutlinedTextField
import com.isakaro.kwik.inputfields.KwikTextField
import com.isakaro.kwik.utils.toFormat
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
            Row(
                modifier = Modifier.weight(1f)
            ) {
                if(outlined){
                    KwikOutlinedTextField(
                        value = dateValue,
                        onValueChange = { dateValue.value = it },
                        visualTransformation = DateVisualTransformation("yyyy-MM-dd"),
                        placeholder = placeholder,
                        singleLine = true,
                        modifier = Modifier
                            .pointerInput(selectedDate) {
                                awaitEachGesture {
                                    awaitFirstDown(pass = PointerEventPass.Initial)
                                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                    if (upEvent != null) {
                                        showDatePicker = true
                                    }
                                }
                            }
                    )
                } else {
                    KwikTextField(
                        modifier = Modifier
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
                        singleLine = true,
                        visualTransformation = DateVisualTransformation("yyyy-MM-dd")
                    )
                }
            }
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

class DateVisualTransformation(private val format: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateMaskFilter(text, format)
    }
}

fun dateMaskFilter(text: AnnotatedString, format: String): TransformedText {
    val regex = Regex("^(d{2}|d{1})[\\W](M{2}|M{1})[\\W](y{4})\$|^(y{4})[\\W](M{2}|M{1})[\\W](d{2}|d{1})\$|^(d{2})[\\W](M{2})[\\W](y{4})\$")
    if (!regex.matches(format.replace('d', 'd').replace('M', 'M').replace('y', 'y'))) {
        return TransformedText(text, OffsetMapping.Identity)
    }

    // Identify the separator
    val separator = format.first { !it.isLetterOrDigit() }

    // Parse the structure: [yyyy, MM, dd]
    val parts = format.split(separator)
    val lengths = parts.map {
        when {
            it.contains("y") -> 4
            it.contains("M") -> 2
            it.contains("d") -> 2
            else -> it.length
        }
    }
    val maxLength = lengths.sum()

    // Sanitize input
    val raw = text.text.filter { it.isDigit() }.take(maxLength)

    // Construct transformed text
    val transformed = buildString {
        var index = 0
        for ((i, len) in lengths.withIndex()) {
            val end = (index + len).coerceAtMost(raw.length)
            append(raw.substring(index, end))
            if (end < raw.length && i < lengths.lastIndex) append(separator)
            index = end
        }
    }

    // Separator positions for offset mapping
    val separatorPositions = lengths.dropLast(1).runningReduce(Int::plus)

    val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            var shift = 0
            for (pos in separatorPositions) {
                if (offset > pos) shift++
            }
            return (offset + shift).coerceAtMost(transformed.length)
        }

        override fun transformedToOriginal(offset: Int): Int {
            var shift = 0
            for (pos in separatorPositions) {
                if (offset > pos + shift) shift++
            }
            return (offset - shift).coerceAtMost(raw.length)
        }
    }

    return TransformedText(AnnotatedString(transformed), offsetMapping)
}

