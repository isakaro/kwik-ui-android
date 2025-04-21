package com.isakaro.kwik.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.text.KwikText

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
    containerColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = MaterialTheme.shapes.medium,
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
 * @param imageHeight A value between 1 and 5 (inclusive).
 * Determines the image height relative to the card height.
 * Each step increases by 20%, so:
 * 1 = 20%, 2 = 40%, 3 = 60%, 4 = 80%, 5 = 100%.
 * @param title String title of the card
 * @param onClick () -> Unit called when card is clicked
 * @param content @Composable () -> Unit content of the card
 * */
@Composable
fun KwikImageCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = MaterialTheme.shapes.medium,
    image: Any,
    imageHeight: Int = 2,
    title: String? = null,
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    KwikCard(
        modifier = modifier,
        containerColor = containerColor,
        shape = shape,
        elevation = elevation,
        onClick = {
            onClick()
        }
    ) {
        BoxWithConstraints {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.height(this@BoxWithConstraints.maxHeight * (imageHeight / 5f))
                ) {
                    KwikImageView(
                        url = image
                    )
                    if(title != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.5f)
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(0F to Color.Transparent, 0F to Color.Transparent, 3F to Color.Black),
                                )
                        )
                        KwikText.TitleSmall(
                            modifier = Modifier.align(Alignment.BottomStart).padding(horizontal = 8.dp, vertical = 8.dp),
                            text = title,
                            color = Color.White,
                            maxLines = 2
                        )
                    }
                }
                content()
            }
        }
    }
}

/**
 * A card component with an image and a title
 *
 * @param modifier Modifier
 * @param image Any image to be displayed. Can be a URL or a resource ID. Note that it'll fill the whole card
 * @param onClick () -> Unit called when card is clicked
 * @param content @Composable () -> Unit content of the card
 * */
@Composable
fun KwikImageCardHorizontal(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = MaterialTheme.shapes.medium,
    image: Any,
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    KwikCard(
        modifier = modifier,
        containerColor = containerColor,
        shape = shape,
        elevation = elevation,
        onClick = {
            onClick()
        }
    ) {
        BoxWithConstraints {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                KwikImageView(
                    modifier = Modifier.width(this@BoxWithConstraints.maxWidth / 3),
                    url = image
                )
                content()
            }
        }
    }
}