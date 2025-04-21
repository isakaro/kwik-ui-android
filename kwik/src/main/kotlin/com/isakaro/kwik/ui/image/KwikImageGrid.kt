package com.isakaro.kwik.ui.image

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.text.KwikText

@Composable
fun BoxWithConstraintsScope.KwikImageGrid(
    photos: List<Any>,
    total: Int,
    onClick: (Any) -> Unit = {}
) {
    val cellWidth = this.maxWidth / 3
    val cellHeight = this.maxHeight / 2

    photos.forEachIndexed { index, photo ->
        when(index){
            0 -> {
                val columnSpan = 2 * cellWidth.value
                val rowSpan = 1 * cellHeight.value

                val modifier = Modifier
                    .offset(
                        x = cellWidth * 0,
                        y = cellHeight * 0
                    )
                    .width(columnSpan.dp)
                    .height(rowSpan.dp)
                    .padding(4.dp)

                Box(
                    modifier = modifier.clickable(onClick = { onClick(photo) })
                ){
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        )
                    ) {
                        KwikImageLoader(
                            url = photos[index],
                        )
                    }
                }
            }
            1 -> {
                val columnSpan = 1 * cellWidth.value
                val rowSpan = 1 * cellHeight.value

                val modifier = Modifier
                    .offset(
                        x = cellWidth * 2,
                        y = cellHeight * 0
                    )
                    .width(columnSpan.dp)
                    .height(rowSpan.dp)
                    .padding(4.dp)

                Box(
                    modifier = modifier.clickable(onClick = { onClick(photo) })
                ){
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        )
                    ) {
                        KwikImageLoader(
                            url = photos[index],
                        )
                    }
                }
            }
            2 -> {
                val columnSpan = 1 * cellWidth.value
                val rowSpan = 1 * cellHeight.value

                val modifier = Modifier
                    .offset(
                        x = cellWidth * 0,
                        y = cellHeight * 1
                    )
                    .width(columnSpan.dp)
                    .height(rowSpan.dp)
                    .padding(4.dp)

                Box(
                    modifier = modifier.clickable(onClick = { onClick(photo) })
                ){
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        )
                    ) {
                        KwikImageLoader(
                            url = photos[index],
                        )
                    }
                }
            }
            3 -> {
                val columnSpan = 1 * cellWidth.value
                val rowSpan = 1 * cellHeight.value

                val modifier = Modifier
                    .offset(
                        x = cellWidth * 1,
                        y = cellHeight * 1
                    )
                    .width(columnSpan.dp)
                    .height(rowSpan.dp)
                    .padding(4.dp)

                Box(
                    modifier = modifier.clickable(onClick = { onClick(photo) })
                ){
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        )
                    ) {
                        KwikImageLoader(
                            url = photos[index],
                        )
                    }
                }
            }
            4 -> {
                val columnSpan = 1 * cellWidth.value
                val rowSpan = 1 * cellHeight.value

                val modifier = Modifier
                    .offset(
                        x = cellWidth * 2,
                        y = cellHeight * 1
                    )
                    .width(columnSpan.dp)
                    .height(rowSpan.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp))

                Box(
                    modifier = modifier.clickable(onClick = { onClick(photo) })
                ) {
                    KwikImageLoader(
                        url = photos[index],
                    )
                    if(total > 5){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            KwikText.TitleMedium(
                                text = "+${total - 5}",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}