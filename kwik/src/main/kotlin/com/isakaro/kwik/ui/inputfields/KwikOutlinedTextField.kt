package com.isakaro.kwik.ui.inputfields

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.isakaro.kwik.R
import com.isakaro.kwik.ui.loading.KwikCircularLoading
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.theme.KwikColorHint
import com.isakaro.kwik.ui.theme.KwikColorSuccess
import com.isakaro.kwik.ui.utils.text
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object AllowedChars {
    val NUMBERS = Regex("[^0-9]")
    val ALPHABETS = Regex("[^a-zA-Z]")
    val ALPHANUMERIC = Regex("[^a-zA-Z0-9]")
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
 * @param showClearTextButton: If true, the clear text button will be shown.
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
    shape: Shape = MaterialTheme.shapes.medium,
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
    showClearTextButton: Boolean = false,
    isLoading: Boolean = false,
    isBigTextField: Boolean = false,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    suggestionsModifier: Modifier = Modifier,
    onSuggestionSelected: (String) -> Unit = {},
    suggestions: List<String> = listOf(),
    suggestionsContainerColor: Color = MaterialTheme.colorScheme.surface,
    delay: Boolean = false,
    delayDuration: Long = 500L,
    colors: TextFieldColors = kwikOutlinedTextFieldColors(enabled)
) {
    var fieldContentType by remember { mutableStateOf<ContentType?>(null) }
    val autofillTypes = mutableListOf<AutofillType>()

    if(keyboardType == KeyboardType.Password){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            fieldContentType = ContentType.Password
        } else {
            autofillTypes.add(AutofillType.Password)
        }
    }
    if(keyboardType == KeyboardType.Email){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            fieldContentType = ContentType.EmailAddress + ContentType.Username
        } else {
            autofillTypes.add(AutofillType.EmailAddress)
            autofillTypes.add(AutofillType.Username)
        }
    }
    if(keyboardType == KeyboardType.Phone){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            fieldContentType = ContentType.PhoneNumber + ContentType.PhoneNumberDevice + ContentType.PhoneNumberNational
        } else {
            autofillTypes.add(AutofillType.PhoneNumber)
            autofillTypes.add(AutofillType.PhoneNumberDevice)
            autofillTypes.add(AutofillType.PhoneNumberNational)
        }
    }
    var suggestionsVisible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    var filteredSuggestions by remember { mutableStateOf(suggestions.take(10)) }
    var textFieldPosition by remember { mutableStateOf<Offset?>(null) }
    var textFieldSize by remember { mutableStateOf<IntSize?>(null) }
    var lastInputType by remember { mutableStateOf(LastInputType.TYPING) }

    fun updateSuggestions(suggestion: String? = null){
        filteredSuggestions = filteredSuggestions.filter { it.lowercase() != (suggestion ?: value.text).lowercase() }
        lastInputType = LastInputType.SUGGESTION
        suggestionsVisible = filteredSuggestions.isNotEmpty()
    }

    var passwordVisible by remember { mutableStateOf(false) }

    val autofill = LocalAutofill.current

    val autofillNode = AutofillNode(
        autofillTypes = autofillTypes.toList(),
        onFill = {
            if(it.length <= maxLength){
                debounceJob?.cancel()
                debounceJob = coroutineScope.launch {
                    if(delay && delayDuration >= 1L) {
                        delay(delayDuration)
                    }

                    // filter suggestions based on the query
                    filteredSuggestions = suggestions.filter { suggestion ->
                        suggestion.contains(it, ignoreCase = true)
                    }

                    if(lastInputType == LastInputType.TYPING){
                        updateSuggestions()
                    } else {
                        lastInputType = LastInputType.TYPING
                    }

                    //trim the text to remove any leading or trailing spaces if not password field
                    if(keyboardType == KeyboardType.Password){
                        onValueChange(value.value.copy(text = it, selection = TextRange(it.length)))
                    } else {
                        onValueChange(value.value.copy(text = it.trim(), selection = TextRange(it.length)))
                    }
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
                    debounceJob?.cancel()
                    debounceJob = coroutineScope.launch {
                        if(delay && delayDuration >= 1L) {
                            delay(delayDuration)
                        }

                        // filter suggestions based on the query
                        filteredSuggestions = suggestions.filter { suggestion ->
                            suggestion.contains(it.text, ignoreCase = true)
                        }

                        if(lastInputType == LastInputType.TYPING){
                            updateSuggestions()
                        } else {
                            lastInputType = LastInputType.TYPING
                        }

                        if(allowedChars != null) {
                            onValueChange(it.copy(allowedChars.replace(it.text, "")))
                        } else onValueChange(it)
                    }
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
            textStyle = textStyle,
            visualTransformation = if(visualTransformation is PasswordVisualTransformation) {
                if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(if(isBigTextField) 150.dp else 45.dp)
                .alpha(if (enabled) 1.0f else 0.5f)
                .then(modifier)
                .onFocusChanged { focusState ->
                    suggestionsVisible = focusState.isFocused
                    autofill?.run {
                        if (focusState.isFocused) {
                            requestAutofillForNode(autofillNode)
                        } else {
                            cancelAutofillForNode(autofillNode)
                        }
                    }
                    if(focusState.isFocused){
                        updateSuggestions()
                    }
                    onFocusChanged(focusState.isFocused)
                }.onGloballyPositioned { layoutCoordinates ->
                    autofillNode.boundingBox = layoutCoordinates.boundsInWindow()
                    textFieldPosition = layoutCoordinates.positionInParent()
                    textFieldSize = layoutCoordinates.size
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
                    if(showClearTextButton && value.value.text.isNotEmpty()){
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clickable {
                                    onValueChange(TextFieldValue(""))
                                    filteredSuggestions = suggestions.take(10)
                                    suggestionsVisible = true
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
                KwikText.LabelMedium(
                    text = hint,
                    color = KwikColorHint,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
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
                            .padding(top = 4.dp)
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
                            .padding(top = 4.dp)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
        if(filteredSuggestions.isNotEmpty() && textFieldPosition != null){
            Popup(
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = {
                    suggestionsVisible = false
                },
                offset = IntOffset(
                    x = textFieldPosition!!.x.toInt(),
                    y = (textFieldPosition!!.y + (textFieldSize!!.height)).toInt()
                )
            ) {
                AnimatedVisibility(
                    visible = suggestionsVisible,
                    enter = fadeIn() + slideInVertically { -it },
                    exit = fadeOut() + slideOutVertically { -it }
                ) {
                    Column(
                        modifier = suggestionsModifier
                            .width(textFieldSize!!.width.dp)
                            .padding(horizontal = 6.dp)
                            .background(
                                color = suggestionsContainerColor,
                                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            filteredSuggestions.forEach { suggestion ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .padding(horizontal = 4.dp)
                                        .clickable {
                                            lastInputType = LastInputType.SUGGESTION
                                            onValueChange(value.value.copy(text = suggestion, selection = TextRange(suggestion.length)))
                                            onSuggestionSelected(suggestion)
                                            updateSuggestions(suggestion)
                                            suggestionsVisible = false
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    KwikText.BodyMedium(
                                        text = suggestion
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun kwikOutlinedTextFieldColors(
    enabled: Boolean = true
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
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        disabledBorderColor = if(enabled) Color.Unspecified else Color.Gray,
        disabledTextColor = if(enabled) Color.Unspecified else Color.Gray,
        errorBorderColor = MaterialTheme.colorScheme.error,
        errorLabelColor = MaterialTheme.colorScheme.error,
        errorPlaceholderColor = MaterialTheme.colorScheme.error,
        errorTextColor = MaterialTheme.colorScheme.onSurface,
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
        visualTransformation = VisualTransformation.None,
        placeholder = "Jack Sparrow",
        keyboardType = KeyboardType.Phone,
        imeAction = ImeAction.Done,
    )
}

@Composable
@Preview(showBackground = true)
private fun KwikErrorOutlinedTextFieldPreview() {
    KwikOutlinedTextField(
        value = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) },
        onValueChange = {},
        visualTransformation = VisualTransformation.None,
        placeholder = "Jack Sparrow",
        isError = true,
        keyboardType = KeyboardType.Phone,
        imeAction = ImeAction.Done,
    )
}