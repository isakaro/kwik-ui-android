package com.isakaro.kwik.ui.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.loading.KwikCircularLoading
import com.isakaro.kwik.ui.text.KwikText

/**
* Simple text button
 *
 * @param modifier Modifier
 * @param text can be a string, int or AnnotatedString
 * @param containerColor Color sets the background color
 * @param textColor Color sets the text color
 * @param shape Shape sets the shape of the button
 * @param isLoading Boolean indicates if button is loading
 * @param onClick () -> Unit called when button is clicked
* */
@Composable
fun KwikTextButton(
    modifier: Modifier = Modifier,
    text: Any,
    containerColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    contentPadding: PaddingValues = PaddingValues(2.dp),
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier.heightIn(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContentColor = containerColor.copy(alpha = 0.5f),
        ),
        contentPadding = contentPadding,
        shape = shape,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if(isLoading){
                KwikCircularLoading(modifier = Modifier.size(20.dp))
            }
            when (text) {
                is String -> {
                    KwikText.BodyMedium(
                        text = text,
                        color = textColor
                    )
                }
                is Int -> {
                    KwikText.BodyMedium(
                        text = stringResource(text),
                        color = textColor
                    )
                }
                is AnnotatedString -> {
                    KwikText.BodyMedium(
                        text = text,
                        color = textColor
                    )
                }
            }
        }
    }
}

/**
 * Simple text button
 *
 * @param modifier Modifier
 * @param text content of the button
 * @param containerColor Color sets the background color
 * @param isLoading Boolean indicates if button is loading
 * @param onClick () -> Unit called when button is clicked
 * */
@Composable
fun KwikTextButton(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    containerColor: Color = Color.Transparent,
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
            text()
        }
    }
}

@Preview
@Composable
private fun KwikTextButtonPreview() {
    KwikTextButton(text = "Button")
}