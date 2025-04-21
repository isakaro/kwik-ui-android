package com.isakaro.kwik.ui.grid

import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A data class representing a single item in a grid.
 *
 * @param colSpan: The number of columns this item spans.
 * @param rowSpan: The number of rows this item spans.
 * @param colPosition: The position of this item in the grid, measured in columns.
 * @param rowPosition: The position of this item in the grid, measured in rows.
 * @param onClick: The action to perform when this item is clicked.
 * @param content: The content of this item.
 * */
data class KwikDiv(
    val colSpan: Int = 1,
    val rowSpan: Int = 1,
    val colPosition: Int,
    val rowPosition: Int,
    val onClick: () -> Unit,
    val content: @Composable () -> Unit
)

@Composable
fun KwikGrid(
    modifier: Modifier = Modifier,
    cols: Int = 1,
    rows: Int = 1,
    gap: Int = 4,
    items: List<KwikDiv>
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val cellWidth = this.maxWidth / cols
        val cellHeight = this.maxHeight / rows

        items.forEachIndexed { index, gridItem ->
            val columnSpan = gridItem.colSpan * cellWidth.value
            val rowSpan = gridItem.rowSpan * cellHeight.value

            Log.d("KwikGrid", "columnSpan: $columnSpan, rowSpan: $rowSpan")

            val modifier = Modifier
                .offset(
                    x = cellWidth * gridItem.colPosition,
                    y = cellHeight * gridItem.rowPosition
                )
                .width(columnSpan.dp)
                .height(rowSpan.dp)
                .padding(gap.dp)

            KwikWidget(
                modifier = modifier,
                item = gridItem
            )
        }
    }
}