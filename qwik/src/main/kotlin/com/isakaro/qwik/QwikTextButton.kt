package com.isakaro.qwik

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isakaro.qwik.theme.ColorPrimaryAccent

@Composable
fun QwikTextButton(
    modifier: Modifier = Modifier,
    text: Any,
    color: Color = Color.Transparent,
    textColor: Color = ColorPrimaryAccent,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContentColor = color.copy(alpha = 0.5f),
        ),
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(8.dp),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Row {
            if(isLoading){
                QwikCircularLoading(modifier = Modifier.size(20.dp))
            }
            when (text) {
                is String -> {
                    Text(
                        text = text,
                        textAlign = TextAlign.End,
                        color = textColor,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
                is Int -> {
                    Text(
                        text = stringResource(text),
                        textAlign = TextAlign.End,
                        color = textColor,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
                is AnnotatedString -> {
                    Text(
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