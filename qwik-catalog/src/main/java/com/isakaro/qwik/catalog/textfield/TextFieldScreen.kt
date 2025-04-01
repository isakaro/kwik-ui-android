package com.isakaro.qwik.catalog.textfield

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.appcatalog.R
import com.isakaro.qwik.catalog.common.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.common.ShowCase
import com.isakaro.qwik.AmpersandPhoneNumberField.AmpersandPhoneNumberField
import com.isakaro.qwik.AmpersandTextField.AmpersandTextField
import com.isakaro.qwik.theme.Theme.AmpersandTheme

@Composable
internal fun TextFieldScreen() {
    val context = LocalContext.current
    val otp = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    ScrollableShowCaseContainer {
        ShowCase(title = "Standard field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {

                }
            )
        }
        ShowCase(title = "Password field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                onKeyboardDone = {

                }
            )
        }
        ShowCase(title = "Phone number field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandPhoneNumberField(
                value = text,
                onValueChange = { value, countryCode ->
                    text.value = value
                },
                onKeyboardDone = {

                },
                isTextCounterShown = true,
                error = R.string.error,
                placeholder = R.string.phone_number,
                imeAction = ImeAction.Done,
            )
        }

        ShowCase(title = "Field with Error") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                isError = true,
                placeholder = R.string.field,
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {

                }
            )
        }
        ShowCase(title = "Disabled field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                isEnabled = false,
                onKeyboardDone = {

                }
            )
        }
        ShowCase(title = "OTP field") {
            OtpField(
                onComplete = {
                    otp.value = it
                    Toast.makeText(context, "Valid OTP: ${it.text}", Toast.LENGTH_SHORT).show()
                }
            )
        }
        ShowCase(title = "Field with Counter") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                isTextCounterShown = true,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {

                }
            )
        }
        ShowCase(title = "Field with Hint") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                hint = R.string.hint,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {

                }
            )
        }
        ShowCase(title = "Field with leading icon") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Search,
                isClearTextBtnShown = true,
                onKeyboardDone = {

                }
            )
        }
        ShowCase(title = "Field with valid input") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                isValid = true,
                isClearTextBtnShown = true,
                onKeyboardDone = {

                }
            )
        }

        ShowCase(title = "Field with action button") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                imeAction = ImeAction.Done,
                trailingIcon = R.drawable.qr_code_scanner,
                keyboardType = KeyboardType.Text,
                onActionClick = {
                    Toast.makeText(context, "I've been clicked ;)", Toast.LENGTH_SHORT).show()
                },
                onKeyboardDone = {

                }
            )
        }

        ShowCase(title = "Field in loading state") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 35,
                imeAction = ImeAction.Done,
                isValid = true,
                isLoading = true,
                keyboardType = KeyboardType.Text,
                isClearTextBtnShown = true,
                onKeyboardDone = {

                }
            )
        }

        ShowCase(title = "Large text field with counter") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            AmpersandTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = R.string.field,
                maxLength = 200,
                imeAction = ImeAction.Default,
                keyboardType = KeyboardType.Text,
                isClearTextBtnShown = true,
                isBigTextField = true,
                onKeyboardDone = {

                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        TextFieldScreen()
    }
}

private const val MAX_CHARACTERS = 5
