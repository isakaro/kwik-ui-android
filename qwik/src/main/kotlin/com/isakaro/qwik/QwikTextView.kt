package com.isakaro.qwik

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

object QwikText {

    @Composable
    fun TitleText(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = Color.DarkGray,
        fontWeight: FontWeight = FontWeight.Bold,
        textAlign: TextAlign = TextAlign.Start,
        style: TextStyle = MaterialTheme.typography.titleMedium,
    ) {
        when (text) {
            is String -> Text(
                text = text,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                modifier = modifier
            )
            is Int -> Text(
                text = text.toString(),
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                modifier = modifier
            )
            is AnnotatedString -> Text(
                text = text,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                modifier = modifier
            )
        }
    }

    @Composable
    fun BodyText(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = Color.DarkGray,
        textAlign: TextAlign = TextAlign.Start,
        style: TextStyle = MaterialTheme.typography.bodyMedium,
    ) {
        when (text) {
            is String -> Text(
                text = text,
                style = style,
                color = color,
                textAlign = textAlign,
                modifier = modifier
            )
            is Int -> Text(
                text = text.toString(),
                style = style,
                color = color,
                textAlign = textAlign,
                modifier = modifier
            )
            is AnnotatedString -> Text(
                text = text,
                style = style,
                color = color,
                textAlign = textAlign,
                modifier = modifier
            )
        }
    }

}