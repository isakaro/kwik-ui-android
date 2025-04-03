package com.isakaro.kwik.catalog.outlinedtextfield

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.Kwik.KwikToast
import com.isakaro.Kwik.catalog.R
import com.isakaro.Kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.Kwik.catalog.ShowCase
import com.isakaro.Kwik.navigator
import com.isakaro.Kwik.rememberKwikToastState
import com.isakaro.Kwik.showToast
import com.isakaro.Kwik.textfield.KwikOutlinedOTP
import com.isakaro.Kwik.textfield.KwikOutlinedPhoneNumberField
import com.isakaro.Kwik.textfield.KwikOutlinedTextField
import com.isakaro.Kwik.theme.Theme.KwikTheme
import com.isakaro.Kwik.utils.countryList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun OutlinedTextFieldScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val context = LocalContext.current
    val otp = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val KwikToastState = rememberKwikToastState()
    var isPhoneNumberValid by remember { mutableStateOf(false) }

    KwikToast(state = KwikToastState)

    ScrollableShowCaseContainer(
        title = "Outlined Text field",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Standard field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Username",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {
                    KwikToastState.showToast("keyboard done")
                }
            )
        }
        ShowCase(title = "Password field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Password",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                onKeyboardDone = {
                    KwikToastState.showToast("keyboard done")
                }
            )
        }

        ShowCase(title = "Phone number field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedPhoneNumberField(
                initialCountryInfo = countryList.random(),
                value = text,
                placeholder = "Phone number",
                isValid = isPhoneNumberValid,
                onValueChange = {
                    text.value = it
                    // for demo purposes, we'll mark the field as valid if it's 8 digits long or above
                    isPhoneNumberValid = it.text.length >= 8
                },
                onCountrySelected = { country ->
                    KwikToastState.showToast("Selected country: ${country.name}")
                }
            )
        }

        ShowCase(title = "Field with Error") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                isError = true,
                placeholder = "Address",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {
                    KwikToastState.showToast("keyboard done")
                }
            )
        }

        ShowCase(title = "Disabled field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                enabled = false,
                onKeyboardDone = {
                    KwikToastState.showToast("keyboard done")
                }
            )
        }

        ShowCase(title = "OTP field") {
            KwikOutlinedOTP(
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

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                isTextCounterShown = true,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {
                    KwikToastState.showToast("keyboard done")
                }
            )
        }
        ShowCase(title = "Field with Hint") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                hint = "This is a hint",
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {
                    KwikToastState.showToast("keyboard done")
                }
            )
        }
        ShowCase(title = "Field with leading icon") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
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

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
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

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
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

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
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

            KwikOutlinedTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Description",
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
private fun PreviewStartScreen() {
    KwikTheme {
        OutlinedTextFieldScreen()
    }
}
