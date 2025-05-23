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
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.ui.toast.KwikToast
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.toast.rememberKwikToastState
import com.isakaro.kwik.ui.toast.showToast
import com.isakaro.kwik.ui.inputfields.KwikOTP
import com.isakaro.kwik.ui.inputfields.KwikPhoneNumberField
import com.isakaro.kwik.ui.inputfields.KwikTextField
import com.isakaro.kwik.theme.KwikTheme
import com.isakaro.kwik.ui.utils.countryList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikTextFieldScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val context = LocalContext.current
    val otp = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val kwikToastState = rememberKwikToastState()

    KwikToast(state = kwikToastState)

    ScrollableShowCaseContainer(
        title = "Filled Text field",
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

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Username",
                placeholder = "Enter email or phone",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                }
            )
        }
        ShowCase(title = "Password field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Password",
                placeholder = "Enter password",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                }
            )
        }
        ShowCase(title = "Phone number field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }
            var isPhoneNumberValid by remember { mutableStateOf(false) }

            KwikPhoneNumberField(
                initialCountryInfo = countryList.random(),
                value = text,
                label = "Phone number",
                isValid = isPhoneNumberValid,
                onValueChange = {
                    text.value = it
                    // for demo purposes, we'll mark the field as valid if it's 8 digits long or above
                    isPhoneNumberValid = it.text.length >= 8
                },
                onCountrySelected = { country ->
                    kwikToastState.showToast("Country selected: ${country.name}")
                }
            )
        }

        ShowCase(title = "With flags") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }
            var isPhoneNumberValid by remember { mutableStateOf(false) }

            KwikPhoneNumberField(
                initialCountryInfo = countryList.random(),
                value = text,
                showFlags = true,
                label = "Phone number",
                isValid = isPhoneNumberValid,
                onValueChange = {
                    text.value = it
                    // for demo purposes, we'll mark the field as valid if it's 8 digits long or above
                    isPhoneNumberValid = it.text.length >= 8
                },
                onCountrySelected = { country ->
                    kwikToastState.showToast("Country selected: ${country.name}")
                }
            )
        }

        ShowCase(title = "With flag and country code") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }
            var isPhoneNumberValid by remember { mutableStateOf(false) }

            KwikPhoneNumberField(
                initialCountryInfo = countryList.random(),
                value = text,
                showFlags = true,
                showCountryCode = true,
                label = "Phone number",
                isValid = isPhoneNumberValid,
                onValueChange = {
                    text.value = it
                    // for demo purposes, we'll mark the field as valid if it's 8 digits long or above
                    isPhoneNumberValid = it.text.length >= 8
                },
                onCountrySelected = { country ->
                    kwikToastState.showToast("Country selected: ${country.name}")
                }
            )
        }

        ShowCase(title = "Field with suggestions") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                showClearTextButton = true,
                placeholder = "Enter address",
                suggestions = listOf("Tortuga", "Isla de Muerta", "Shipwreck Cove", "Davy Jones' Locker"),
                error = "Incorrect address",
            )
        }

        ShowCase(title = "Field with Error") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                isError = true,
                error = "Incorrect address",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                }
            )
        }

        ShowCase(title = "Disabled field") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                enabled = false
            )
        }

        ShowCase(title = "Field with custom shape") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                maxLength = 35,
                shape = MaterialTheme.shapes.extraLarge
            )
        }

        ShowCase(title = "OTP field") {
            KwikOTP(
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

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                maxLength = 35,
                isTextCounterShown = true
            )
        }
        ShowCase(title = "Field with Hint") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                maxLength = 35,
                hint = "This is a hint",
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onKeyboardDone = {
                    kwikToastState.showToast("keyboard done")
                }
            )
        }
        ShowCase(title = "Field with leading icon") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Search,
                showClearTextButton = true,
                onKeyboardDone = {

                }
            )
        }
        ShowCase(title = "Field with valid input") {
            val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("Tortuga")
                )
            }

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                maxLength = 35,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                isValid = true,
                showClearTextButton = true,
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

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                maxLength = 35,
                imeAction = ImeAction.Done,
                trailingIcon = R.drawable.qr_code_scanner,
                keyboardType = KeyboardType.Text,
                onActionClick = {
                    kwikToastState.showToast("I've been clicked!")
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

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Address",
                placeholder = "Enter address",
                maxLength = 35,
                imeAction = ImeAction.Done,
                isValid = true,
                isLoading = true,
                keyboardType = KeyboardType.Text,
                showClearTextButton = true,
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

            KwikTextField(
                value = text,
                onValueChange = {
                    text.value = it
                },
                label = "Description",
                placeholder = "Write a description",
                maxLength = 200,
                imeAction = ImeAction.Default,
                keyboardType = KeyboardType.Text,
                showClearTextButton = true,
                isBigTextField = true,
                onKeyboardDone = {

                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikTextFieldScreen() {
    KwikTheme {
        KwikTextFieldScreen()
    }
}
