package com.isakaro.ui.catalog.dialog

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.isakaro.appcatalog.R
import com.isakaro.ui.catalog.common.ShowCaseContainer
import app.isakaro.ui.library.AmpersandPhoneNumberField.AmpersandPhoneNumberField
import app.isakaro.ui.library.AmpersandTextField.AmpersandTextField
import app.isakaro.ui.library.theme.Theme.AmpersandTheme

@Composable
internal fun DialogScreen() {
    val context = LocalContext.current

    ShowCaseContainer {
        var openDialog by remember { mutableStateOf(false) }
        var openConfirmDialog by remember { mutableStateOf(false) }
        var openNonCancellableDialog by remember { mutableStateOf(false) }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        ) {
            AmpersandButton(text = "Open Content Dialog", onClick = { openDialog = true })
            AmpersandButton(text = "Open Confirm Dialog", onClick = { openConfirmDialog = true })
            AmpersandButton(text = "Open Non Cancellable Dialog", onClick = { openNonCancellableDialog = true })
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
            AmpersandDialog.AmpersandContentDialog(
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
                        Text(text = "I am a dialog with custom content", color = Color.Black, style = MaterialTheme.typography.headlineMedium)
                        Column(
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(text = "Enter your phone number and name", style = MaterialTheme.typography.titleSmall, color = Color.Black)
                            AmpersandPhoneNumberField(
                                value = text,
                                onValueChange = { value, countryCode -> text.value = value },
                                placeholder = R.string.phone_number,
                            )
                            AmpersandTextField(
                                value = text1,
                                onValueChange = { text1.value = it },
                                placeholder = R.string.field
                            )
                            AmpersandCheckBox(
                                text = R.string.agree_to_terms,
                                checked = false,
                                onCheckedChange = { }
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                AmpersandButton(text = "Cancel", buttonStyle = AmpersandButton.ButtonStyle.TEXT, onClick = { openDialog = false })
                                AmpersandButton(text = "Confirm", buttonStyle = AmpersandButton.ButtonStyle.TEXT, onClick = { openDialog = false })
                            }
                        }
                    }
                }
            }
        }

        if(openConfirmDialog){
            AmpersandDialog.AmpersandConfirmDialog(
                open = openConfirmDialog,
                onConfirm = {
                    Toast.makeText(context, "Confirmed ;)", Toast.LENGTH_SHORT).show()
                },
                dismiss = {
                    openConfirmDialog = false
                }
            ){
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "A quick brown fox jumps over the lazy dog. A quick brown fox jumps over the lazy dog. A quick brown fox jumps over the lazy dog",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )
                }
            }
        }

        if(openNonCancellableDialog){
            AmpersandDialog.AmpersandConfirmDialog(
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
                    Icon(
                        painter = painterResource(id = app.isakaro.ui.library.R.drawable.baseline_visibility_24),
                        tint = Color.Black,
                        contentDescription = "The eye",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "Accept the terms and conditions to proceed",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        DialogScreen()
    }
}
