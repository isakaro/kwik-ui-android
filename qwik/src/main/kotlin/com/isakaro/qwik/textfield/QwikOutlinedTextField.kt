package com.isakaro.qwik.lifecycle.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.R
import com.isakaro.qwik.theme.QwikColorHint
import com.isakaro.qwik.theme.QwikColorSuccess

object AllowedChars {
    val ALPHANUMERIC = Regex("[^A-Za-z0-9 ]")
    val NUMBERS = Regex("[^0-9]")
    val ALL = null
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QwikOutlinedTextField(
    modifier: Modifier = Modifier,
    value: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit = {  },
    onActionClick: () -> Unit = {  },
    onFocusChanged: (Boolean) -> Unit = {  },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isEditable: Boolean = true,
    placeholder: String,
    shape: Shape = RoundedCornerShape(8.dp),
    isError: Boolean = false,
    error: String = "Field is required",
    isSingleLine: Boolean = true,
    maxLength: Int = 35,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines: Int = 1,
    allowedChars: Regex? = AllowedChars.ALL,
    imeAction: ImeAction = ImeAction.Done,
    isValid: Boolean = false,
    isTextCounterShown: Boolean = false,
    hint: Any? = null,
    hintVisibleOnError: Boolean = false,
    leadingIcon: Any? = null,
    trailingIcon: Any? = null,
    isClearTextBtnShown: Boolean = false,
    isLoading: Boolean = false,
    isBigTextField: Boolean = false,
    isEnabled: Boolean = true,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        cursorColor = Color.Black,
        focusedContainerColor = Color.White,
        focusedLabelColor = Color.Gray,
        focusedPlaceholderColor = Color.Black,
        focusedBorderColor = Color.Gray,
        unfocusedBorderColor = Color.Gray,
        unfocusedLabelColor = Color.Gray,
        unfocusedPlaceholderColor = Color.Gray,
        unfocusedTextColor = Color.Black,
        disabledBorderColor = if(isEditable) Color.Unspecified else Color.Gray,
        disabledTextColor = if(isEditable) Color.Unspecified else Color.Gray,
        errorBorderColor = MaterialTheme.colorScheme.error,
        errorLabelColor = MaterialTheme.colorScheme.error,
        errorPlaceholderColor = MaterialTheme.colorScheme.error,
        errorTextColor = Color.Black,
        errorCursorColor = MaterialTheme.colorScheme.error
    )
) {

    val autofillTypes = mutableListOf<AutofillType>()

    if(keyboardType == KeyboardType.Password){
        autofillTypes.add(AutofillType.Password)
    }
    if(keyboardType == KeyboardType.Email){
        autofillTypes.add(AutofillType.EmailAddress)
    }
    if(keyboardType == KeyboardType.Phone){
        autofillTypes.add(AutofillType.PhoneNumber)
        autofillTypes.add(AutofillType.PhoneNumberDevice)
        autofillTypes.add(AutofillType.PhoneNumberNational)
    }

    var passwordVisible by remember { mutableStateOf(false) }
    val autofill = LocalAutofill.current
    val autofillNode = AutofillNode(
        autofillTypes = autofillTypes.toList(),
        onFill = {
            if(it.length <= maxLength){
                //trim the text to remove any leading or trailing spaces if not password field
                if(keyboardType == KeyboardType.Password){
                    onValueChange(value.value.copy(text = it, selection = TextRange(it.length)))
                } else {
                    onValueChange(value.value.copy(text = it.trim(), selection = TextRange(it.length)))
                }
            }
        }
    )

    LocalAutofillTree.current += autofillNode

    Column {
        OutlinedTextField(
            value = value.value,
            onValueChange = {
                if(!isEnabled) return@OutlinedTextField
                if(it.text.length <= maxLength){
                    if(allowedChars != null) {
                        onValueChange(it.copy(allowedChars.replace(it.text, "")))
                    } else onValueChange(it)
                }
            },
            label = {
                Text(
                    text = placeholder,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            isError = isError,
            enabled = isEnabled && isEditable,
            textStyle = MaterialTheme.typography.bodyLarge,
            visualTransformation = if(visualTransformation is PasswordVisualTransformation) {
                if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(if (isBigTextField) 150.dp else 68.dp)
                .alpha(if (isEnabled) 1.0f else 0.5f)
                .then(modifier)
                .onGloballyPositioned {
                    autofillNode.boundingBox = it.boundsInWindow()
                }
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                    autofill?.run {
                        if (focusState.isFocused) {
                            requestAutofillForNode(autofillNode)
                        } else {
                            cancelAutofillForNode(autofillNode)
                        }
                    }
                },
            singleLine = isSingleLine && !isBigTextField,
            maxLines = if(isBigTextField) 8 else maxLines,
            shape = shape,
            leadingIcon = if(leadingIcon != null){
                {
                    Row(modifier = Modifier
                        .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(leadingIcon is Int){
                            Icon(
                                painter = painterResource(id = leadingIcon),
                                tint = Color.Black,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        } else if(leadingIcon is ImageVector) {
                            Icon(
                                imageVector = leadingIcon,
                                tint = Color.Black,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }
            } else {
                null
            },
            trailingIcon = {
                Row(modifier = Modifier
                    .padding(end = 12.dp, start = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(trailingIcon is Int){
                        Icon(
                            painter = painterResource(id = trailingIcon),
                            tint = Color.Black,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    onActionClick()
                                }
                        )
                    } else if(trailingIcon is ImageVector) {
                        Icon(
                            imageVector = trailingIcon,
                            tint = Color.Black,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    onActionClick()
                                }
                        )
                    }

                    if(visualTransformation is PasswordVisualTransformation) {
                        PasswordToggle(passwordVisible) {
                            passwordVisible = !passwordVisible
                        }
                    }
                    if(isClearTextBtnShown && value.value.text.isNotEmpty()){
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clickable {
                                    onValueChange(TextFieldValue(""))
                                },
                            contentDescription = "Clear text",
                            tint = Color.Black
                        )
                    }
                    if(isLoading){
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            color = Color.Black
                        )
                    }
                    if(isValid){
                        Icon(
                            modifier = Modifier.size(20.dp),
                            contentDescription = null,
                            imageVector = Icons.Filled.CheckCircle,
                            tint = QwikColorSuccess
                        )
                    }
                    if(isError) {
                        Icon(
                            imageVector = Icons.Filled.Info, "field error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            colors = colors,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = keyboardType,
            ),
            keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (hint != null && (hintVisibleOnError && isError || !hintVisibleOnError)) {
                when(hint){
                    is Int -> {
                        Text(
                            text = stringResource(id = hint),
                            color = QwikColorHint,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    is String -> {
                        if(hint.isNotBlank()){
                            Text(
                                text = hint,
                                color = QwikColorHint,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    is AnnotatedString -> {
                        if(hint.isNotBlank()){
                            Text(
                                text = hint,
                                color = QwikColorHint,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if(isError){
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .align(Alignment.BottomEnd),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                if(isTextCounterShown || isBigTextField) {
                    Text(
                        text = "${value.value.text.length}/$maxLength",
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .align(Alignment.BottomEnd),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun PasswordToggle(passwordVisible: Boolean, onClick: () -> Unit) {
    val image = if (passwordVisible) {
        ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)
    } else {
        ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
    }

    Icon(
        imageVector = image,
        contentDescription = if (passwordVisible) "Hide password" else "Show password",
        tint = Color.Black,
        modifier = Modifier
            .size(25.dp)
            .clickable { onClick() }
    )
}

@Composable
@Preview(showBackground = true)
fun QwikOutlinedTextFieldPreview() {
    QwikOutlinedTextField(
        value = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) },
        onValueChange = {},
        placeholder = "Jack Sparrow",
        keyboardType = KeyboardType.Phone,
        visualTransformation = VisualTransformation.None,
        imeAction = ImeAction.Done,
    )
}