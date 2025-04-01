package com.isakaro.qwik

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

data class IsakaroWidgetItem(
    val image: String? = null,
    val title: String,
    val onClickAction: () -> Unit
)

data class IsakaroGridItem(
    val image: String? = null,
    val color: Brush? = null,
    val colSpan: Int = 1,
    val rowSpan: Int = 1,
    val colPosition: Int,
    val rowPosition: Int,
    val title: String,
    val onClickAction: () -> Unit
)

@Composable
fun BoxWithConstraintsScope.QwikGridBuilder(
    cols: Int = 1,
    rows: Int = 1,
    items: List<IsakaroGridItem>
) {
    val cellWidth = this.maxWidth / cols
    val cellHeight = this.maxHeight / rows

    items.forEachIndexed { index, gridItem ->
        val columnSpan = gridItem.colSpan * cellWidth.value
        val rowSpan = gridItem.rowSpan * cellHeight.value

        val modifier = Modifier
            .offset(
                x = cellWidth * gridItem.colPosition,
                y = cellHeight * gridItem.rowPosition
            )
            .width(columnSpan.dp)
            .height(rowSpan.dp)
            .padding(4.dp)

        IsakaroWidget(
            modifier = modifier,
            isakaroGridItem = gridItem
        ) {
            gridItem.onClickAction()
        }
    }
}