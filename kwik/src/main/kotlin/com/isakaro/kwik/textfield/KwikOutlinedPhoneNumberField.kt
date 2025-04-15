package com.isakaro.kwik.textfield

import androidx.compose.foundation.isSystemInDarkTheme
import com.isakaro.kwik.countrypicker.KwikCountryCodeButton
import com.isakaro.kwik.countrypicker.KwikCountryPickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.theme.KwikColorSuccess
import com.isakaro.kwik.theme.KwikTheme
import com.isakaro.kwik.utils.KwikCountryInfo
import com.isakaro.kwik.utils.countryList
import com.isakaro.kwik.utils.text

val allowedChars = Regex("^[0-9]*$")

/**
 * A powerful phone number field that allows the user to select a country code from a list of countries.
 * The field is outlined and has a leading icon that shows the selected country code.
 * The field also has a trailing icon that shows a check mark if the phone number is valid.
 *
 * @param value The current value of the field
 * @param onValueChange The callback that is called when the value of the field changes
 * @param onKeyboardDone The callback that is called when the keyboard done button is clicked
 * @param placeholder The placeholder text of the field
 * @param shape The shape of the field
 * @param isError Whether the field is in an error state
 * @param error The error message to show when the field is in an error state
 * @param singleLine Whether the field should be single line
 * @param maxLines The maximum number of lines the field can have
 * @param imeAction The action that should be performed when the keyboard done button is clicked
 * @param initialCountryInfo The initial country info to show in the field
 * @param isValid Whether the phone number is valid
 * @param enabled Whether the field is enabled
 * @param countrySelectable Whether the country code is selectable
 * @param colors The colors of the field
 * @param onCountrySelected The callback that is called when a country is selected
 *
 * Example usage:
 *
 * ```
 * val value = remember { mutableStateOf(TextFieldValue("1234567890")) }
 *
 * KwikOutlinedPhoneNumberField(
 *    value = value,
 *    onValueChange = {
 *      value.value = it
 *    },
 *    onKeyboardDone = {},
 *    placeholder = "Phone number",
 *    initialCountryInfo = countryList.random()
 *    onCountrySelected = {},
 *    isValid = true
 *)
 * */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KwikOutlinedPhoneNumberField(
    value: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit = {  },
    placeholder: String,
    shape: Shape = RoundedCornerShape(8.dp),
    isError: Boolean = false,
    error: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Done,
    initialCountryInfo: KwikCountryInfo,
    isValid: Boolean = false,
    enabled: Boolean = true,
    countrySelectable: Boolean = true,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = Color.Transparent,
        focusedLabelColor = Color.Gray,
        focusedPlaceholderColor = Color.Gray,
        focusedBorderColor = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
        unfocusedBorderColor = Color.Gray,
        unfocusedLabelColor = Color.Gray,
        unfocusedPlaceholderColor = Color.Gray,
        unfocusedTextColor = Color.Black,
        disabledBorderColor = if(enabled) Color.Unspecified else Color.Gray,
        disabledTextColor = if(enabled) Color.Unspecified else Color.Gray,
        errorBorderColor = MaterialTheme.colorScheme.error,
        errorLabelColor = MaterialTheme.colorScheme.error,
        errorPlaceholderColor = MaterialTheme.colorScheme.error,
        errorTextColor = MaterialTheme.colorScheme.error,
        errorCursorColor = MaterialTheme.colorScheme.error
    ),
    onCountrySelected: (KwikCountryInfo) -> Unit = {}
) {

    var countryCodePickerWidth by remember { mutableStateOf(0.dp) }
    var showCountryPicker by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val countryListState = rememberLazyListState()
    var selectedCountryInfo by remember { mutableStateOf(initialCountryInfo) }

    KwikCountryPickerDialog(
        open = showCountryPicker,
        countryListState = countryListState,
        onDismiss = {
            showCountryPicker = false
        },
        onSelect = { country ->
            showCountryPicker = false
            selectedCountryInfo = country
            onCountrySelected(country)
        }
    )

    Column {
        KwikText.BodyMedium(
            text = placeholder,
            color = Color.Gray,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        val autofill = LocalAutofill.current
        val autofillNode = AutofillNode(
            autofillTypes = listOf(AutofillType.PhoneNumberNational),
            onFill = {
                if(value.text.matches(allowedChars) && value.text.length <= 12){
                    onValueChange(value.value.copy(text = it, selection = TextRange(it.length)))
                }
            }
        )

        LocalAutofillTree.current += autofillNode

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            OutlinedTextField(
                value = value.value,
                onValueChange = {
                    if (it.text.matches(allowedChars) && it.text.length <= 12) {
                        onValueChange(it)
                    }
                },
                isError = isError,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned {
                        autofillNode.boundingBox = it.boundsInWindow()
                    }.onFocusChanged { focusState ->
                        autofill?.run {
                            if (focusState.isFocused) {
                                requestAutofillForNode(autofillNode)
                            } else {
                                cancelAutofillForNode(autofillNode)
                            }
                        }
                    },
                singleLine = singleLine,
                maxLines = maxLines,
                enabled = enabled,
                shape = shape,
                trailingIcon = {
                    if (isError) Icon(Icons.Filled.Info, "field error", tint = MaterialTheme.colorScheme.error)
                    if(isValid){
                        Icon(
                            modifier = Modifier.size(20.dp),
                            contentDescription = null,
                            imageVector = Icons.Filled.CheckCircle,
                            tint = KwikColorSuccess
                        )
                    }
                },
                leadingIcon = {
                    Spacer(modifier = Modifier.width(countryCodePickerWidth.value.dp - 12.dp)) // 12 is the padding of CountryCodeButton
                },
                colors = colors,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = imeAction,
                    keyboardType = KeyboardType.Phone,
                ),
                keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
            )
            KwikCountryCodeButton(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .height(55.dp)
                    .onGloballyPositioned {
                        countryCodePickerWidth = with(density) {
                            it.size.width.toDp()
                        }
                    },
                country = selectedCountryInfo,
                enabled = countrySelectable
            ){
                showCountryPicker = true
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        if(isError){
            KwikText.LabelMedium(
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun KwikOutlinedPhoneNumberFieldPreview() {
    val value = remember { mutableStateOf(TextFieldValue("1234567890")) }

    KwikTheme {
        KwikOutlinedPhoneNumberField(
            value = value,
            onValueChange = {},
            onKeyboardDone = {},
            placeholder = "Phone number",
            initialCountryInfo = countryList.random()
        )
    }
}