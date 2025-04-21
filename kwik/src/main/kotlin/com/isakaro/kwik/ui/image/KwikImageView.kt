package com.isakaro.kwik.ui.image

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

/**
 * An image view that can load images from resources, urls or vectors
 *
 * @param modifier Modifier
 * @param url Any type of url or resource. Can be a string, a drawable resource or a vector resource
 * @param tint Color to apply as tint. Only applies to icon and vector images
 * @param contentScale ContentScale to apply to the image
 * @param contentDescription String? content description for accessibility
 * @param shape Shape? shape to apply to the image
 * */
@Composable
fun KwikImageView(
    modifier: Modifier = Modifier,
    url: Any,
    tint: Color = Color.Unspecified,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    shape: Shape? = null,
){
    when (url) {
        is Int -> {
            Icon(
                modifier = modifier.apply {
                    if (shape != null){
                        clip(shape)
                    }
                },
                painter = painterResource(id = url),
                tint = tint,
                contentDescription = contentDescription
            )
        }

        is ImageVector -> {
            Icon(
                modifier = modifier.apply {
                    if (shape != null){
                        clip(shape)
                    }
                },
                imageVector = url,
                tint = tint,
                contentDescription = contentDescription
            )
        }

        is String -> {
            KwikImageLoader(
                modifier = modifier,
                contentScale = contentScale,
                contentDescription = contentDescription,
                url = url,
                shape = shape
            )
        }
    }
}