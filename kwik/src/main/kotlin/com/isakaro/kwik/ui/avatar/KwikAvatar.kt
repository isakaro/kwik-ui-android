package com.isakaro.kwik.ui.avatar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.card.KwikCard
import com.isakaro.kwik.ui.image.KwikImageView

/**
 * An avatar component that uses the KwikImageView to load images
 *
 * @param modifier Modifier
 * @param url Any type of url or resource. Can be a string, a drawable resource or a vector resource
 * */
@Composable
fun KwikAvatar(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    url: Any
) {

    KwikCard(
        modifier = modifier,
        shape = shape,
        containerColor = Color.Transparent,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        KwikImageView(
            url = url,
            modifier = modifier,
            contentDescription = null,
            shape = shape,
            contentScale = ContentScale.Crop
        )
    }

}