package com.isakaro.qwik

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight

@Composable
fun QwikFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
    onLongPress: (Boolean) -> Unit = {}
) {
    FilterChip(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress(!isSelected) },
                    onTap = { onClick(!isSelected) }
                )
            },
        selected = isSelected,
        onClick = {
            onClick(!isSelected)
        },
        label = {
            Text(
                text = text,
                fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if(isSelected) Color.White else Color.Black
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.White
        ),
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "checked",
                    tint = Color.White,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else null
    )
}

@Composable
fun QwikChip(
    text: String,
    onClick: () -> Unit
) {
    FilterChip(
        selected = false,
        onClick = {
            onClick()
        },
        label = {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.White
        )
    )
}