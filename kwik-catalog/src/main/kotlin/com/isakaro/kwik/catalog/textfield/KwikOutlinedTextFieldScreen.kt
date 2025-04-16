package com.isakaro.kwik.catalog.textfield

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.Kwik.catalog.R
import com.isakaro.kwik.toast.KwikToast
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.textfield.KwikOutlinedOTP
import com.isakaro.kwik.toast.rememberKwikToastState
import com.isakaro.kwik.toast.showToast
import com.isakaro.kwik.textfield.KwikOutlinedPhoneNumberField
import com.isakaro.kwik.textfield.KwikOutlinedTextField
import com.isakaro.kwik.theme.KwikTheme
import com.isakaro.kwik.utils.countryList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikOutlinedTextFieldScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val context = LocalContext.current
    val otp = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val kwikToastState = rememberKwikToastState()
    var isPhoneNumberValid by remember { mutableStateOf(false) }

    KwikToast(state = kwikToastState)

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
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Username",
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        }
        ShowCase(title = "Password field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = "Password",
                maxLength = 35,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        }

        ShowCase(title = "") {
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
                    kwikToastState.showToast("Selected country: ${country.name}")
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
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                isError = true,
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        }

        ShowCase(title = "Disabled field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                enabled = false,
                imeAction = ImeAction.Done
            )
        }

        ShowCase(title = "Field with custom shape") {
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
                shape = MaterialTheme.shapes.extraLarge,
                maxLength = 35
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
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                isTextCounterShown = true
            )
        }
        ShowCase(title = "Field with Hint") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                hint = "This is a hint"
            )
        }
        ShowCase(title = "Field with leading icon") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                onKeyboardDone = {

                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                leadingIcon = Icons.Default.Search,
                showClearTextButton = true
            )
        }
        ShowCase(title = "Field with valid input") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                onKeyboardDone = {

                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                isValid = true,
                showClearTextButton = true
            )
        }

        ShowCase(title = "Field with action button") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                onKeyboardDone = {

                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                onActionClick = {
                    Toast.makeText(context, "I've been clicked ;)", Toast.LENGTH_SHORT).show()
                },
                placeholder = "Address",
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                trailingIcon = R.drawable.qr_code_scanner
            )
        }

        ShowCase(title = "Field in loading state") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                onKeyboardDone = {

                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                placeholder = "Address",
                maxLength = 35,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                isValid = true,
                showClearTextButton = true,
                isLoading = true
            )
        }

        ShowCase(title = "Large text field with counter") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikOutlinedTextField(
                onKeyboardDone = {

                },
                value = text,
                onValueChange = {
                    text.value = it
                },
                isBigTextField = true,
                placeholder = "Description",
                maxLength = 200,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default,
                showClearTextButton = true
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikOutlinedTextFieldScreen() {
    KwikTheme {
        KwikOutlinedTextFieldScreen()
    }
}
