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
import com.isakaro.qwik.IsakaroCard

@Composable
fun QwikWidget(
    modifier: Modifier = Modifier,
    isakaroGridItem: IsakaroGridItem,
    onClick: () -> Unit
) {

    IsakaroCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .apply {
                    if(isakaroGridItem.color != null && isakaroGridItem.image.isNullOrBlank()){
                        background(brush = isakaroGridItem.color)
                    }
                }
        ) {
            if(isakaroGridItem.image != null){
                IsakaroImageLoader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    url = isakaroGridItem.image,
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(brush = Brush.verticalGradient(0F to Color.Transparent, 0F to Color.Transparent, 3F to Color.Black))
                    .align(Alignment.BottomCenter)
            ) {
                IsakaroText.TitleText(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart),
                    text = isakaroGridItem.title,
                    color = Color.White,
                )
            }
        }
    }

}

@Composable
fun QwikWidget(
    isakaroWidgetItem: IsakaroWidgetItem,
    onClick: () -> Unit
) {

    IsakaroCard(
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if(isakaroWidgetItem.image != null){
                IsakaroImageLoader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    url = isakaroWidgetItem.image,
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(brush = Brush.verticalGradient(0F to Color.Transparent, 0F to Color.Transparent, 3F to Color.Black))
                    .align(Alignment.BottomCenter)
            ) {
                IsakaroText.TitleText(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart),
                    text = isakaroWidgetItem.title,
                    color = Color.White,
                )
            }
        }
    }

}