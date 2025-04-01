package com.isakaro.qwik

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.R

@Composable
fun QwikImageView(
    modifier: Modifier = Modifier,
    url: Any
){
    when (url) {
        is Int -> {
            Icon(
                modifier = modifier,
                painter = painterResource(id = url),
                tint = Color.Gray,
                contentDescription = null
            )
        }

        is ImageVector -> {
            Icon(
                modifier = modifier,
                imageVector = url,
                tint = Color.Gray,
                contentDescription = null
            )
        }

        is String -> {
            IsakaroImageLoader(
                modifier = modifier,
                url = url
            )
        }
    }
}

@Preview
@Composable
fun QwikImageViewPreview(){
    QwikImageView(
        url = painterResource(id = R.drawable.shield)
    )
}