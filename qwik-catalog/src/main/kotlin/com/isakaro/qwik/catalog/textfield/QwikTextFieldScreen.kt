package com.isakaro.qwik.catalog.textfield

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikOTP
import com.isakaro.qwik.QwikPhoneNumberField
import com.isakaro.qwik.catalog.R
import com.isakaro.qwik.catalog.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.textfield.QwikTextField
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.isakaro.qwik.utils.countryList

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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Field",
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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = stringResource(R.string.field),
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

            QwikPhoneNumberField(
                selectedCountryInfo = countryList.random(),
                value = text,
                placeholder = "Phone number",
                onValueChange = {

                },
            )
        }

        ShowCase(title = "Field with Error") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                isError = true,
                placeholder = stringResource(R.string.field),
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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = stringResource(R.string.field),
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                enabled = false,
                onKeyboardDone = {

                }
            )
        }

        ShowCase(title = "OTP field") {
            QwikOTP(
                onValidOTP = {
                    otp.value = it
                    Toast.makeText(context, "Valid OTP: ${it.text}", Toast.LENGTH_SHORT).show()
                },
                error = "Invalid OTP",
            )
        }

        ShowCase(title = "Field with Counter") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "This is a field",
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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = stringResource(R.string.field),
                maxLength = 35,
                hint = "This is a hint",
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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = stringResource(R.string.field),
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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = stringResource(R.string.field),
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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = stringResource(R.string.field),
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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = stringResource(R.string.field),
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

            QwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = stringResource(R.string.field),
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
    QwikTheme {
        TextFieldScreen()
    }
}
