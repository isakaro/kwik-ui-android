package com.isakaro.qwik

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QwikSimpleButton(
    text: String,
    isLoading: Boolean = false,
    loadingText: String = "",
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.alpha(if (isLoading) 0.5f else 1.0f)
            .clickable(
                indication = if(isLoading) null else LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ){

            },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        enabled = enabled,
        onClick = { if(isLoading) { /* do nothing onClick while loading */ } else { onClick() } },
    ) {
        if(isLoading){
            Spacer(modifier = Modifier.width(4.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(25.dp),
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            if(loadingText.isNotBlank()){
                Text(
                    text = loadingText,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun QwikSimpleButtonPreview() {
    QwikSimpleButton(text = "Click me", isLoading = true) {}
}