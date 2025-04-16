package com.isakaro.kwik.grid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.card.KwikCard
import com.isakaro.kwik.text.KwikText

@Composable
fun KwikWidget(
    modifier: Modifier = Modifier,
    item: KwikDiv
) {
    KwikCard(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) {
        item.content()
    }
}

@Preview
@Composable
private fun KwikWidgetPreview() {
    KwikWidget(
        item = KwikDiv(
            colSpan = 2,
            rowSpan = 2,
            colPosition = 0,
            rowPosition = 0,
            onClick = {}
        ){
            KwikText.BodyMedium(text = "Content")
        }
    )
}