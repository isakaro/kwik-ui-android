package com.isakaro.kwik.ui.grid

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.ui.text.KwikText

@Composable
fun KwikWidget(
    modifier: Modifier = Modifier,
    item: KwikDiv
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent
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