package com.isakaro.ui.components

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
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
import com.isakaro.ui.theme.ColorPrimaryAccent

@Composable
fun IsakaroOTPField(
    onValidOtp: (TextFieldValue) -> Unit,
    size: Int = 6,
    isError: Boolean = false,
    error: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    clearAll: Boolean = false,
    onKeyboardDone: () -> Unit = {  }
) {
    val focusManager = LocalFocusManager.current

    val otpFields = List(size = size) { rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) } }

    fun validOtp(): Boolean {
        return if(otpFields.none { it.value.text.isEmpty() }){
            onValidOtp(TextFieldValue(
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
                OtpDigit(
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
                    }
                )
            }
            if(visualTransformation is PasswordVisualTransformation) {
                PasswordToggle(passwordVisible) {
                    passwordVisible = !passwordVisible
                }
            }
        }
        if(isError){
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Start,
                style = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

object EmptyTextToolbar: TextToolbar {
    override val status: TextToolbarStatus = TextToolbarStatus.Hidden

    override fun hide() {  }

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?,
    ) {
    }
}

fun String.lastChar(): String {
    return try {
        this.last().toString()
    } catch (e: Exception){
        ""
    }
}

@Composable
fun OtpDigit(
    value: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit = {  },
    onDelete: () -> Unit = {  },
    passwordVisible: Boolean = false,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isLastField: Boolean = false, // if true, will trigger onKeyboardDone when user presses enter key on keyboard
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
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            visualTransformation = if(visualTransformation is PasswordVisualTransformation) {
                if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else VisualTransformation.None,
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Transparent,
                errorCursorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                focusedBorderColor = if(value.value.text.isEmpty()) Color.Black else ColorPrimaryAccent,
                unfocusedBorderColor = if(value.value.text.isEmpty()) Color.Black else ColorPrimaryAccent,
                unfocusedContainerColor = Color.White
            ),
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
