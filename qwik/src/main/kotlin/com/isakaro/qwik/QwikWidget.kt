package com.isakaro.qwik

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QwikWidget(
    modifier: Modifier = Modifier,
    item: QwikDiv,
    onClick: () -> Unit
) {

    QwikCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .apply {
                    if(item.color != null && item.background.isNullOrBlank()){
                        background(brush = item.color)
                    }
                }
        ) {
            if(item.background != null){
                QwikImageLoader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    url = item.background,
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(brush = Brush.verticalGradient(0F to Color.Transparent, 0F to Color.Transparent, 3F to Color.Black))
                    .align(Alignment.BottomCenter)
            ) {
                QwikText.TitleText(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart),
                    text = item.title,
                    color = Color.White,
                )
            }
        }
    }

}