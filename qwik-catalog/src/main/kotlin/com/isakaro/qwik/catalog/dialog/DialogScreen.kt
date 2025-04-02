package com.isakaro.qwik.catalog.dialog

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
import com.isakaro.qwik.QwikButton
import com.isakaro.qwik.QwikCheckBox
import com.isakaro.qwik.QwikDialog
import com.isakaro.qwik.QwikTextButton
import com.isakaro.qwik.textfield.QwikPhoneNumberField
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.R
import com.isakaro.qwik.textfield.QwikTextField
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.isakaro.qwik.utils.countryList

@Composable
internal fun DialogScreen() {
    val context = LocalContext.current

    ShowCaseContainer {
        var openDialog by remember { mutableStateOf(false) }
        var openConfirmDialog by remember { mutableStateOf(false) }
        var openNonCancellableDialog by remember { mutableStateOf(false) }
        var checked by remember { mutableStateOf(false) }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        ) {
            QwikButton(text = "Open Content Dialog", onClick = { openDialog = true })
            QwikButton(text = "Open Confirm Dialog", onClick = { openConfirmDialog = true })
            QwikButton(text = "Open Non Cancellable Dialog", onClick = { openNonCancellableDialog = true })
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
            QwikDialog.ContentDialog(
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
                            QwikPhoneNumberField(
                                initialCountryInfo = countryList.random(),
                                value = text,
                                placeholder = "Phone number",
                                onValueChange = {
                                    text.value = it
                                },
                            )
                            QwikTextField(
                                value = text1,
                                onValueChange = { text1.value = it },
                                label = "Name",
                                placeholder = "Enter your name",
                            )
                            QwikCheckBox(
                                text = "Agree to terms",
                                checked = checked,
                                onCheckedChange = { checked = it }
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                QwikTextButton(text = "Cancel", onClick = { openDialog = false })
                                QwikButton(text = "Confirm", onClick = { openDialog = false })
                            }
                        }
                    }
                }
            }
        }

        if(openConfirmDialog){
            QwikDialog.ConfirmDialog(
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
            QwikDialog.ConfirmDialog(
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
                        painter = painterResource(id = R.drawable.baseline_visibility_24),
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
    QwikTheme {
        DialogScreen()
    }
}
