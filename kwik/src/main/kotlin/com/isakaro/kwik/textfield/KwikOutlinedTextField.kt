package com.isakaro.kwik.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.isakaro.kwik.loading.KwikCircularLoading
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.R
import com.isakaro.kwik.theme.KwikColorHint
import com.isakaro.kwik.theme.KwikColorSuccess

object AllowedChars {
    val ALPHANUMERIC = Regex("[^A-Za-z0-9 ]")
    val NUMBERS = Regex("[^0-9]")
    val ALL = null
}

/**
 * A versatile filled text field component that can be used to take user input.
 * @param modifier: The modifier for the text field.
 * @param value: The value of the text field.
 * @param onValueChange: The callback that will be called when the value of the text field changes.
 * @param onKeyboardDone: The callback that will be called when the keyboard action is done.
 * @param onActionClick: The callback that will be called when the action icon is clicked.
 * @param onFocusChanged: The callback that will be called when the focus of the text field changes.
 * @param visualTransformation: The visual transformation of the text field. Refer to [VisualTransformation].
 * @param isEditable: If true, the text field is editable. Default is true.
 * @param placeholder: The placeholder text of the text field.
 * @param shape: The shape of the text field. Default is [RoundedCornerShape].
 * @param isError: If true, the text field will display an error state.
 * @param error: The error message to display when isError is true.
 * @param singleLine: If true, the text field will be single line. Default is true.
 * @param maxLength: The maximum length of the text field. Default is 35.
 * @param keyboardType: The keyboard type of the text field. Default is [KeyboardType.Text].
 * @param keyboardActions: The keyboard actions of the text field. Default is [KeyboardActions] with onDone action.
 * @param maxLines: The maximum number of lines of the text field. Default is 1.
 * @param allowedChars: The allowed characters in the text field. Default is [AllowedChars.ALL].
 * @param imeAction: The IME action of the text field. Default is [ImeAction.Done].
 * @param isValid: If true, the text field will display a valid state.
 * @param isTextCounterShown: If true, the text counter will be shown.
 * @param hint: The hint text of the text field.
 * @param hintVisibleOnError: If true, the hint will be visible only when there is an error.
 * @param leadingIcon: The leading icon of the text field.
 * @param trailingIcon: The trailing icon of the text field.
 * @param isClearTextBtnShown: If true, the clear text button will be shown.
 * @param isLoading: If true, the loading indicator will be shown.
 * @param isBigTextField: If true, the text field will be big.
 * @param enabled: If true, the text field will be enabled. Default is true.
 * @param colors: The colors of the text field. Default is [OutlinedTextFieldDefaults.colors].
 *
 * Example usage:
 *
 * ```
 * val pirateName = rememberSaveable(stateSaver = TextFieldValue.Saver) {
 *    mutableStateOf(TextFieldValue(""))
 * }
 *
 * KwikOutlinedTextField(
 *    value = pirateName,
 *    onValueChange = {
 *      pirateName.value = it
 *    },
 *    placeholder = "Jack Sparrow",
 *    keyboardType = KeyboardType.Phone,
 *    visualTransformation = VisualTransformation.None,
 *    imeAction = ImeAction.Done,
 * )
 * */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KwikOutlinedTextField(
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
    singleLine: Boolean = true,
    maxLength: Int = 65,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions(
        onDone = {
            onKeyboardDone()
        }
    ),
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
    enabled: Boolean = true,
    colors: TextFieldColors = kwikOutlinedTextFieldColors(enabled)
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
                if(!enabled) return@OutlinedTextField
                if(it.text.length <= maxLength){
                    if(allowedChars != null) {
                        onValueChange(it.copy(allowedChars.replace(it.text, "")))
                    } else onValueChange(it)
                }
            },
            label = {
                KwikText.TitleMedium(
                    text = placeholder,
                    textAlign = TextAlign.Start,
                    color = if(isError) MaterialTheme.colorScheme.error else Color.Gray
                )
            },
            isError = isError,
            enabled = enabled && isEditable,
            textStyle = MaterialTheme.typography.bodyLarge,
            visualTransformation = if(visualTransformation is PasswordVisualTransformation) {
                if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(if (isBigTextField) 150.dp else 65.dp)
                .alpha(if (enabled) 1.0f else 0.5f)
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
            singleLine = singleLine && !isBigTextField,
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
                                tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        } else if(leadingIcon is ImageVector) {
                            Icon(
                                imageVector = leadingIcon,
                                tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
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
                            tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
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
                            tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
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
                            tint = if(isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                    if(isLoading){
                        KwikCircularLoading(
                            modifier = Modifier.size(30.dp),
                            color = if(isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                    if(isValid){
                        Icon(
                            modifier = Modifier.size(20.dp),
                            contentDescription = null,
                            imageVector = Icons.Filled.CheckCircle,
                            tint = KwikColorSuccess
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
            keyboardActions = keyboardActions,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (hint != null && (hintVisibleOnError && isError || !hintVisibleOnError)) {
                KwikText.RenderText(
                    text = hint,
                    color = KwikColorHint,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if(isError){
                    KwikText.LabelMedium(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .align(Alignment.BottomStart)
                    )
                }
                if(isTextCounterShown || isBigTextField) {
                    KwikText.LabelMedium(
                        text = "${value.value.text.length}/$maxLength",
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}

@Composable
fun kwikOutlinedTextFieldColors(
    enabled: Boolean
): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
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
    )
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
        tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
        modifier = Modifier
            .size(25.dp)
            .clickable { onClick() }
    )
}

@Composable
@Preview(showBackground = true)
private fun KwikOutlinedTextFieldPreview() {
    KwikOutlinedTextField(
        value = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) },
        onValueChange = {},
        placeholder = "Jack Sparrow",
        keyboardType = KeyboardType.Phone,
        visualTransformation = VisualTransformation.None,
        imeAction = ImeAction.Done,
    )
}

@Composable
@Preview(showBackground = true)
private fun KwikErrorOutlinedTextFieldPreview() {
    KwikOutlinedTextField(
        value = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) },
        onValueChange = {},
        isError = true,
        placeholder = "Jack Sparrow",
        keyboardType = KeyboardType.Phone,
        visualTransformation = VisualTransformation.None,
        imeAction = ImeAction.Done,
    )
}