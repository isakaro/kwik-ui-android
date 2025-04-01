package com.isakaro.qwik

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.theme.QwikColorSuccess
import com.isakaro.qwik.utils.CountryInfo
import com.isakaro.qwik.utils.countryList
import com.isakaro.qwik.utils.text

val allowedChars = Regex("^[0-9]*$")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QwikPhoneNumberField(
    value: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit = {  },
    placeholder: Int,
    shape: Shape = RoundedCornerShape(8.dp),
    isError: Boolean = false,
    error: String = "",
    isSingleLine: Boolean = true,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Done,
    selectedCountryInfo: CountryInfo,
    isValid: Boolean = false,
    enabled: Boolean = true,
    countrySelectable: Boolean = true,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        cursorColor = Color.Black,
        focusedContainerColor = Color.White,
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        disabledBorderColor = Color.Gray,
        unfocusedContainerColor = Color.White,
        errorBorderColor = MaterialTheme.colorScheme.error,
        errorCursorColor = MaterialTheme.colorScheme.error
    ),
    onShowCountryList: () -> Unit = {}
) {

    var countryCodePickerWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column {
        Text(
            text = stringResource(id = placeholder),
            color = Color.DarkGray,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1f).width(10.dp))

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
                singleLine = isSingleLine,
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
                            tint = QwikColorSuccess
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
            QwikCountryCodeButton(
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
                onShowCountryList()
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        if(isError){
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
fun QwikPhoneNumberFieldPreview() {
    val value = remember { mutableStateOf(TextFieldValue("1234567890")) }

    QwikPhoneNumberField(
        value = value,
        onValueChange = {},
        onKeyboardDone = {},
        placeholder = 0,
        selectedCountryInfo = countryList.random()
    )
}