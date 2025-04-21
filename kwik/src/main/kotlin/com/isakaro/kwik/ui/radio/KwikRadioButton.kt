package com.isakaro.kwik.ui.radio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.text.KwikText

/**
* RadioButton composable that uses KwikText.BodyText for the text
 *
 * @param text The text to display next to the radio button
 * @param selected Whether the radio button is selected
 * @param onClick The callback to be called when the radio button is clicked
* */
@Composable
fun KwikRadioButton(
    text: String,
    selected: Boolean,
    onClick: (Boolean) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.padding(end = 4.dp),
            colors = RadioButtonDefaults.colors(),
            selected = selected,
            onClick = null
        )
        KwikText.TitleSmall(
            modifier = Modifier.clickable {
                onClick(!selected)
            },
            text = text,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun KwikRadioButtonPreview(){
    KwikRadioButton(
        text = "Tortuga",
        selected = true,
        onClick = {}
    )
}