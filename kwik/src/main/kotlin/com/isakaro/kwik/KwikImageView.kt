package com.isakaro.kwik

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

/**
* An image view that can load images from resources, urls or vectors
 *
 * @param modifier Modifier
 * @param url Any type of url or resource. Can be a string, a drawable resource or a vector resource
 * @param tint Color to apply as tint
* */
@Composable
fun KwikImageView(
    modifier: Modifier = Modifier,
    url: Any,
    tint: Color = Color.Unspecified,
    contentDescription: String? = null
){
    when (url) {
        is Int -> {
            Icon(
                modifier = modifier,
                painter = painterResource(id = url),
                tint = tint,
                contentDescription = contentDescription
            )
        }

        is ImageVector -> {
            Icon(
                modifier = modifier,
                imageVector = url,
                tint = tint,
                contentDescription = contentDescription
            )
        }

        is String -> {
            KwikImageLoader(
                modifier = modifier,
                contentDescription = contentDescription,
                url = url
            )
        }
    }
}

@Preview
@Composable
private fun KwikImageViewPreview(){
    KwikImageView(
        url = painterResource(id = R.drawable.shield)
    )
}