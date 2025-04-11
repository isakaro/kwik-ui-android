package com.isakaro.kwik

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

/**
 * An avatar component that uses the KwikImageView to load images
 *
 * @param modifier Modifier
 * @param url Any type of url or resource. Can be a string, a drawable resource or a vector resource
 * */
@Composable
fun KwikAvatar(
    modifier: Modifier = Modifier,
    url: Any
) {

    KwikCard {
        KwikImageView(
            url = url,
            modifier = modifier,
            contentDescription = null,
            shape = CircleShape,
            contentScale = ContentScale.Crop
        )
    }

}