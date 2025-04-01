package com.isakaro.qwik

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

Light2


@Composable
fun QwikIconTextButton(
    text: Int,
    icon: Any,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier,
        contentPadding = PaddingValues(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.White,
            containerColor = MaterialTheme.colorScheme.primaryLight2
        ),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        if(icon is Int){
            Icon(
                painter = painterResource(id = icon),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        } else if (icon is ImageVector) {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = stringResource(id = text),
            textAlign = TextAlign.End,
            color = color,
            style = TextStyle(fontSize = 18.sp)
        )
    }
}

@Composable
fun QwikIconButton(
    modifier: Modifier = Modifier,
    icon: Any,
    tint: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier,
        contentPadding = PaddingValues(4.dp),
        shape = RoundedCornerShape(8.dp),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        when(icon){
            is Int -> {
                Icon(
                    painter = painterResource(id = icon),
                    tint = tint,
                    contentDescription = null,
                    modifier = modifier
                )
            }
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    tint = tint,
                    contentDescription = null,
                    modifier = modifier
                )
            }
        }
    }
}