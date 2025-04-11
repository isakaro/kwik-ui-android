package com.isakaro.kwik

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A simple card component
 *
 * @param modifier Modifier
 * @param containerColor Color sets the background color
 * @param roundedCornerShape RoundedCornerShape sets the shape of the card
 * @param elevation CardElevation sets the elevation of the card
 * @param onClick () -> Unit called when card is clicked
 * @param content @Composable () -> Unit content of the card
 * */
@Composable
fun KwikCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(12.dp),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
){
    Card(
        modifier = modifier,
        shape = roundedCornerShape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = elevation,
        onClick = onClick
    ) {
        content()
    }
}