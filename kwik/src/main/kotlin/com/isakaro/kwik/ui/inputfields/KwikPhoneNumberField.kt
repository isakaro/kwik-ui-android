package com.isakaro.kwik.ui.inputfields

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
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
import com.isakaro.kwik.ui.countrypicker.KwikCountryCodeButton
import com.isakaro.kwik.ui.countrypicker.KwikCountryPickerDialog
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.theme.KwikColorSuccess
import com.isakaro.kwik.ui.utils.KwikCountryInfo
import com.isakaro.kwik.ui.utils.countryList
import com.isakaro.kwik.ui.utils.text

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KwikPhoneNumberField(
    modifier: Modifier = Modifier,
    value: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit = {  },
    label: String? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    isError: Boolean = false,
    error: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Done,
    initialCountryInfo: KwikCountryInfo,
    isValid: Boolean = false,
    enabled: Boolean = true,
    countrySelectable: Boolean = true,
    colors: TextFieldColors = kwikTextFieldColors(),
    onCountrySelected: (KwikCountryInfo) -> Unit = {},
    countryPickerTitle: String = "Where are you from?",
    showFlags: Boolean = false,
    showCountryCode: Boolean = false,
    showDialingCode: Boolean = true
) {

    var countryCodePickerWidth by remember { mutableStateOf(0.dp) }
    var showCountryPicker by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val countryListState = rememberLazyListState()
    var selectedCountryInfo by remember { mutableStateOf(initialCountryInfo) }

    KwikCountryPickerDialog(
        title = countryPickerTitle,
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
        if(label != null){
            KwikText.TitleMedium(
                modifier = Modifier.padding(bottom = 4.dp),
                text = label,
                color = if(isSystemInDarkTheme()) Color.Gray else Color.DarkGray,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

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

        Box {
            TextField(
                value = value.value,
                onValueChange = {
                    if (it.text.matches(allowedChars) && it.text.length <= 12) {
                        onValueChange(it)
                    }
                },
                isError = isError,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(65.dp)
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
                    }.then(modifier),
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
                    Spacer(modifier = Modifier.width(countryCodePickerWidth.value.dp - 8.dp)) // 8 is the padding of KwikCountryCodeButton
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
                    .onGloballyPositioned {
                        countryCodePickerWidth = with(density) {
                            it.size.width.toDp()
                        }
                    },
                country = selectedCountryInfo,
                shape = shape,
                enabled = countrySelectable,
                showFlags = showFlags,
                showCountryCode = showCountryCode,
                showDialingCode = showDialingCode
            ){
                showCountryPicker = true
            }
        }
        if(isError){
            Spacer(modifier = Modifier.height(4.dp))
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
private fun KwikPhoneNumberFieldPreview() {
    val value = remember { mutableStateOf(TextFieldValue("1234567890")) }

    KwikPhoneNumberField(
        value = value,
        onValueChange = {},
        onKeyboardDone = {},
        label = "Phone number",
        initialCountryInfo = countryList.random()
    )
}

@Preview
@Composable
private fun KwikPhoneNumberFieldWithFlagPreview() {
    val value = remember { mutableStateOf(TextFieldValue("1234567890")) }

    KwikPhoneNumberField(
        value = value,
        showFlags = true,
        showCountryCode = true,
        onValueChange = {},
        onKeyboardDone = {},
        label = "Phone number",
        initialCountryInfo = countryList.random()
    )
}