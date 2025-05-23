package com.isakaro.kwik.catalog.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.ui.checkbox.KwikCheckBox
import com.isakaro.kwik.ui.dialog.KwikDialog
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.button.KwikTextButton
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.inputfields.KwikPhoneNumberField
import com.isakaro.kwik.ui.inputfields.KwikTextField
import com.isakaro.kwik.theme.KwikTheme
import com.isakaro.kwik.ui.utils.countryList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikDialogScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val context = LocalContext.current

    ShowCaseContainer(
        title = "Dialogs",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        var openDialog by remember { mutableStateOf(false) }
        var openConfirmDialog by remember { mutableStateOf(false) }
        var openNonCancellableDialog by remember { mutableStateOf(false) }
        var checked by remember { mutableStateOf(false) }

        KwikVSpacer(12)

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        ) {
            KwikButton(text = "Open Content Dialog", onClick = { openDialog = true })
            KwikButton(text = "Open Confirm Dialog", onClick = { openConfirmDialog = true })
            KwikButton(text = "Open Non Cancellable Dialog", onClick = { openNonCancellableDialog = true })
        }

        val text = rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue("")
            )
        }
        val text1 = rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue("")
            )
        }

        if(openDialog){
            KwikDialog.ContentDialog(
                modifier = Modifier.padding(16.dp),
                open = openDialog,
                dismiss = {
                    openDialog = false
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp, bottom = 15.dp),
                    ) {
                        KwikText.HeadlineMedium(
                            text = "I am a dialog with custom content"
                        )

                        Column(
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            KwikText.TitleSmall(
                                text = "Enter your phone number and name"
                            )

                            KwikPhoneNumberField(
                                initialCountryInfo = countryList.random(),
                                value = text,
                                label = "Phone number",
                                onValueChange = {
                                    text.value = it
                                },
                            )
                            KwikTextField(
                                value = text1,
                                onValueChange = { text1.value = it },
                                label = "Name",
                                placeholder = "Enter your name",
                            )
                            KwikCheckBox(
                                text = "Agree to terms",
                                checked = checked,
                                onCheckedChange = { checked = it }
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
                            ) {
                                KwikTextButton(text = "Cancel", onClick = { openDialog = false })
                                KwikButton(text = "Confirm", onClick = { openDialog = false })
                            }
                        }
                    }
                }
            }
        }

        if(openConfirmDialog){
            KwikDialog.ConfirmDialog(
                open = openConfirmDialog,
                onConfirm = {
                    openConfirmDialog = false
                },
                dismiss = {
                    openConfirmDialog = false
                }
            ){
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    KwikText.TitleSmall(
                        text = "The only rules that really matter are these: what a man can do and what a man can’t do.",
                    )
                }
            }
        }

        if(openNonCancellableDialog){
            KwikDialog.ConfirmDialog(
                modifier = Modifier.padding(16.dp),
                title = "Terms and Conditions",
                open = openNonCancellableDialog,
                cancellable = false,
                confirmText = "Accept",
                onConfirm = {
                    openNonCancellableDialog = false
                },
                dismiss = {
                    openNonCancellableDialog = false
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    KwikImageView(
                        url = R.drawable.kwikui_logo,
                        modifier = Modifier.size(100.dp).padding(bottom = 4.dp)
                    )
                    KwikText.TitleSmall(
                        text = "Sorry mate, you must accept the terms and conditions to continue, savvy?",
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikDialogScreen() {
    KwikTheme {
        KwikDialogScreen()
    }
}
