package com.isakaro.qwik

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
import com.isakaro.qwik.theme.Theme.QwikTheme

@Composable
fun QwikRadioButton(
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
        QwikText.BodyText(
            modifier = Modifier.clickable {
                onClick(!selected)
            },
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview
@Composable
fun QwikRadioButtonPreview(){
    QwikTheme {
        QwikRadioButton(
            text = "Tortuga",
            selected = true,
            onClick = {}
        )
    }
}