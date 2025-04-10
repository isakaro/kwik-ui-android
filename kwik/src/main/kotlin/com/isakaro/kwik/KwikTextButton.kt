package com.isakaro.kwik

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isakaro.kwik.theme.KwikTheme

/**
* Simple text button
 *
 * @param modifier Modifier
 * @param text can be a string, int or AnnotatedString
 * @param containerColor Color sets the background color
 * @param textColor Color sets the text color
 * @param isLoading Boolean indicates if button is loading
 * @param onClick () -> Unit called when button is clicked
* */
@Composable
fun KwikTextButton(
    modifier: Modifier = Modifier,
    text: Any,
    containerColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.primary,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContentColor = containerColor.copy(alpha = 0.5f),
        ),
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(8.dp),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Row {
            if(isLoading){
                KwikCircularLoading(modifier = Modifier.size(20.dp))
            }
            when (text) {
                is String -> {
                    KwikText.BodyMedium(
                        text = text,
                        textAlign = TextAlign.End,
                        color = textColor,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
                is Int -> {
                    KwikText.BodyMedium(
                        text = stringResource(text),
                        textAlign = TextAlign.End,
                        color = textColor,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
                is AnnotatedString -> {
                    KwikText.BodyMedium(
                        text = text,
                        textAlign = TextAlign.End,
                        color = textColor,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun KwikTextButtonPreview() {
    KwikTheme {
        KwikTextButton(text = "Button")
    }
}