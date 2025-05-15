package com.isakaro.kwik.ui.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.isakaro.kwik.R

/**
 * An image loader that uses Coil to load images.
 *
 * @param modifier The modifier to apply to the image.
 * @param url The URL of the image to load.
 * @param placeholder The placeholder to show while the image is loading.
 * @param contentDescription The content description of the image.
 * @param contentScale The scale of the image.
 * @param loading The action to perform when the image is loading.
 * @param success The action to perform when the image has loaded successfully.
 * @param error The action to perform when the image has failed to load.
 * @param colorFilter The color filter to apply to the image.
 * @param onClick The action to perform when the image is clicked.
 * */
@Composable
fun KwikImageLoader(
    modifier: Modifier = Modifier,
    url: Any,
    shape: Shape? = null,
    @DrawableRes placeholder: Int = R.drawable.ic_placeholder,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    loading: () -> Unit = {},
    success: () -> Unit = {},
    error: () -> Unit = {},
    colorFilter: ColorFilter? = null,
    onClick: () -> Unit = {}
) {
    val loadingState = remember {
        mutableStateMapOf<Any, ImageLoaderState>()
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .build(),
        modifier = modifier
            .fillMaxSize()
            .clickable { onClick() }
            .placeholder(
                visible = loadingState[url] == ImageLoaderState.LOADING,
                highlight = PlaceholderHighlight.shimmer(),
                shape = shape
            ).apply {
                if(shape != null) {
                    clip(shape)
                }
            },
        colorFilter = colorFilter,
        contentDescription = contentDescription,
        contentScale = contentScale,
        error = painterResource(placeholder),
        onLoading = { loadingState[url] = ImageLoaderState.LOADING; loading() },
        onSuccess = { loadingState[url] = ImageLoaderState.SUCCESS; success() },
        onError = { loadingState[url] = ImageLoaderState.ERROR; error() }
    )
}

enum class ImageLoaderState {
    LOADING,
    SUCCESS,
    ERROR
}