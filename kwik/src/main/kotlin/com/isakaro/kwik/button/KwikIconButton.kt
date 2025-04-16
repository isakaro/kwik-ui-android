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
fun KwikIconButton(
    modifier: Modifier = Modifier,
    icon: Any,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick() },
        interactionSource = remember { MutableInteractionSource() },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor
        )
    ) {
        KwikImageView(
            modifier = modifier.padding(4.dp),
            url = icon,
            tint = tint
        )
    }
}

@Composable
fun KwikIconButton(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick() },
        interactionSource = remember { MutableInteractionSource() },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor
        )
    ) {
        icon()
    }
}