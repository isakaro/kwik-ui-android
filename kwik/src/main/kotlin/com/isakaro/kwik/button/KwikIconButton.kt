package com.isakaro.kwik.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.image.KwikImageView
import com.isakaro.kwik.text.KwikText

@Composable
fun KwikIconTextButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Any,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.White,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        KwikImageView(
            url = icon,
            modifier = Modifier.size(35.dp)
        )
        KwikText.BodyMedium(
            modifier = Modifier.padding(end = 8.dp),
            text = text,
            textAlign = TextAlign.End,
            color = color
        )
    }
}

@Composable
fun KwikIconButton(
    modifier: Modifier = Modifier,
    icon: Any,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier,
        interactionSource = remember { MutableInteractionSource() },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor
        )
    ) {
        KwikImageView(
            url = icon,
            tint = tint
        )
    }
}