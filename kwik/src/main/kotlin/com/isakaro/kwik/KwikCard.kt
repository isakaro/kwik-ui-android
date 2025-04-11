package com.isakaro.kwik

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

/**
 * A simple card component
 *
 * @param modifier Modifier
 * @param containerColor Color sets the background color
 * @param shape RoundedCornerShape sets the shape of the card
 * @param elevation CardElevation sets the elevation of the card
 * @param onClick () -> Unit called when card is clicked
 * @param content @Composable () -> Unit content of the card
 * */
@Composable
fun KwikCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(12.dp),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
){
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = elevation,
        onClick = onClick
    ) {
        content()
    }
}

/**
 * A card component with an image and a title
 *
 * @param modifier Modifier
 * @param image Any image to be displayed. Can be a URL or a resource ID. Note that it'll fill the whole card
 * @param title String title of the card
 * @param onClick () -> Unit called when card is clicked
 * @param content @Composable () -> Unit content of the card
 * */
@Composable
fun KwikImageCard(
    modifier: Modifier = Modifier,
    image: Any,
    title: String,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    KwikCard(
        modifier = modifier,
        containerColor = Color.White,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
            ) {
                KwikImageView(
                    url = image
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(0F to Color.Transparent, 0F to Color.Transparent, 3F to Color.Black),
                        )
                )
                KwikText.TitleSmall(
                    modifier = Modifier.align(Alignment.BottomStart).padding(horizontal = 8.dp),
                    text = title,
                    maxLines = 2
                )
                content()
            }
        }
    }
}