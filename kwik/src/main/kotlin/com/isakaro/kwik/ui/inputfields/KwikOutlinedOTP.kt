package com.isakaro.kwik.ui.inputfields

import android.view.KeyEvent.KEYCODE_DEL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isakaro.kwik.ui.text.KwikText

/**
 * OTP input. The OTP field will automatically move focus to the next field when a digit is entered.
 * @param onValidOTP: Callback that is called when a valid OTP is entered. The TextFieldValue contains the OTP.
 * @param size: The number of fields in the OTP. Default is 6.
 * @param isError: If true, the error message will be displayed.
 * @param error: The error message to display.
 * @param visualTransformation: The visual transformation to apply to the OTP fields. Default is [VisualTransformation.None]. Pass in [PasswordVisualTransformation] for password masking.
 * @param clearAll: If true, all OTP fields will be cleared.
 * @param onKeyboardDone: Callback that is called when the user presses the done key on the keyboard.
 *
 * Example usage:
 *
 * ```
 * KwikOTP(
 *    onValidOTP = { otp ->
 *      // handle valid OTP
 *    },
 *    error = "Invalid OTP"
 * )
 * */
@Composable
fun KwikOutlinedOTP(
    onValidOTP: (TextFieldValue) -> Unit,
    size: Int = 6,
    isError: Boolean = false,
    error: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    clearAll: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = kwikOutlinedTextFieldColors(),
    onKeyboardDone: () -> Unit = {  }
) {
    val focusManager = LocalFocusManager.current

    val otpFields = List(size = size) { rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) } }

    fun validOtp(): Boolean {
        return if(otpFields.none { it.value.text.isEmpty() }){
            onValidOTP(TextFieldValue(
                text = otpFields.joinToString("") { it.value.text },
            ))
            true
        } else false
    }

    if(clearAll){
        otpFields.forEach { it.value = TextFieldValue("") }
    }

    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (0 until size).forEach { index ->
                OTPDigit(
                    value = otpFields[index],
                    isLastField = index == 5,
                    isError = isError,
                    onValueChange = {
                        otpFields[index].value = it
                        val isValidOtp = validOtp()
                        if(it.text.length == 1){
                            // don't move focus to next field if the otp field is already filled
                            try {
                                if(!isValidOtp) focusManager.moveFocus(FocusDirection.Right)
                            } catch (e: Exception){}
                        }
                    },
                    onDelete = {
                        // for the first field, we don't want to move focus to previous field as it could try to move focus to a field on the previous page
                        if(index != 0){
                            try {
                                focusManager.moveFocus(FocusDirection.Left)
                            } catch (e: Exception){}
                        }
                    },
                    onKeyboardDone = {
                        onKeyboardDone()
                    },
                    shape = shape,
                    colors = colors
                )
            }
            if(visualTransformation is PasswordVisualTransformation) {
                PasswordToggle(passwordVisible) {
                    passwordVisible = !passwordVisible
                }
            }
        }
        if(isError){
            KwikText.RenderText(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start,
                style = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

private fun String.lastChar(): String {
    return try {
        this.last().toString()
    } catch (e: Exception){
        ""
    }
}

@Composable
private fun OTPDigit(
    value: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit = {  },
    onDelete: () -> Unit = {  },
    passwordVisible: Boolean = false,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isLastField: Boolean = false, // if true, will trigger onKeyboardDone when user presses enter key on keyboard,
    colors: TextFieldColors,
    shape: Shape
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent,
    )
    CompositionLocalProvider(
        LocalTextToolbar provides EmptyTextToolbar,
        LocalTextSelectionColors provides customTextSelectionColors,
    ) {
        OutlinedTextField(
            value = value.value,
            onValueChange = {
                onValueChange(it.copy(text = AllowedChars.NUMBERS.replace(it.text, "").lastChar(), selection = TextRange(it.text.length)))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.None,
                imeAction = if(isLastField) ImeAction.Done else ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword
            ),
            textStyle = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            visualTransformation = if(visualTransformation is PasswordVisualTransformation) {
                if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else VisualTransformation.None,
            isError = isError,
            shape = shape,
            colors = colors,
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .width(50.dp)
                .focusProperties { canFocus = true }
                .onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KEYCODE_DEL) {
                        onDelete()
                    }
                    false
                },
            keyboardActions = KeyboardActions(
                onDone = {
                    onKeyboardDone()
                }
            )
        )
    }
}
