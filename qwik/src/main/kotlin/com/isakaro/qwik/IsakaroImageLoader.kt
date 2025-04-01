package com.isakaro.qwik

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.isakaro.qwik.R

@Composable
fun IsakaroImageLoader(
    modifier: Modifier = Modifier,
    url: Any,
    @DrawableRes placeholder: Int = R.drawable.ic_placeholder,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop,
    loading: () -> Unit = {},
    success: () -> Unit = {},
    error: () -> Unit = {}
) {
    val loadingState = remember {
        mutableStateMapOf<Any, ImageLoaderState>()
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .build(),
        modifier = Modifier.fillMaxSize().then(modifier)
            .placeholder(
                visible = loadingState[url] == ImageLoaderState.LOADING,
                highlight = PlaceholderHighlight.shimmer(),
                shape = MaterialTheme.shapes.medium
            ),
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