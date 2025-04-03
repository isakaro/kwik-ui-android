package com.isakaro.kwik

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun KwikCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(12.dp),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
){
    Card(
        modifier = modifier,
        shape = roundedCornerShape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        content()
    }
}