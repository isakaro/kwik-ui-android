package com.isakaro.kwik

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.theme.KwikTheme
import kotlinx.coroutines.launch

/**
 * Properties for the indicator.
 * @param height The height of the indicator
 * @param width The width of the indicator. It's a percentage of the screen width. For example, if the width is 0.8, the indicator will be 80% of the screen width.
 * @param horizontalPadding The horizontal padding of the indicator
 * @param verticalPadding The vertical padding of the indicator
 * @param borderRadius The border radius of the indicator
 * */
data class KwikBottomIndicatorProps(
    val height: Float = 4f,
    val width: Float = 0.8f,
    val horizontalPadding: Float = 0f,
    val verticalPadding: Float = 0f,
    val borderRadius: Float = 4f
)

/**
 * A versatile horizontal tab pager capable of displaying any content.
 *
 * @param tabs List of tabs to display
 * @param pagerState The state of the pager
 * @param kwikBottomIndicatorProps The properties of the indicator. Refer to [KwikBottomIndicatorProps] for more details.
 * @param containerColor The background color of the tab row
 * @param selectedContentColor The color of the selected tab
 * */
@Composable
fun KwikBottomTab(
    modifier: Modifier = Modifier,
    tabs: List<KwikTabItem>, pagerState: PagerState,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = Color.Gray,
    kwikBottomIndicatorProps: KwikBottomIndicatorProps = KwikBottomIndicatorProps()
) {
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth().then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
            tabs.forEachIndexed { index, tab ->
                KwikBottomTabItemView(
                    selected = pagerState.currentPage == index,
                    item = tab,
                    containerColor = containerColor,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor,
                    kwikBottomIndicatorProps = kwikBottomIndicatorProps,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
    }
}

/**
 * The view for the tab item.
 *
 * @param selected Boolean indicates if the tab is selected
 * @param containerColor Color sets the background color
 * @param selectedContentColor Color sets the color of the selected tab
 * @param unselectedContentColor Color sets the color of the unselected tab
 * @param enableIndicator Boolean indicates if the indicator is enabled
 * @param kwikBottomIndicatorProps [KwikBottomIndicatorProps] the properties of the indicator
 * @param item [KwikBottomTabItem] the tab item
 * @param onClick () -> Unit called when the tab is clicked
 *
 * @see KwikBottomTabItem
 * */
@Composable
fun KwikBottomTabItemView(
    selected: Boolean,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = Color.Transparent,
    enableIndicator: Boolean = false,
    item: KwikTabItem,
    kwikBottomIndicatorProps: KwikBottomIndicatorProps,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = containerColor)
                .align(Alignment.TopCenter)
                .padding(4.dp)
                .clickable(
                    onClick = { onClick() },
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(item.icon != null){
                KwikImageView(
                    modifier = Modifier.size(30.dp),
                    url = item.icon,
                    tint = if(selected) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
            if(item.title != null){
                KwikText.TitleSmall(
                    text = item.title,
                    color = if(selected) MaterialTheme.colorScheme.primary else Color.Gray,
                )
            }
        }
        if(enableIndicator){
            Spacer(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = kwikBottomIndicatorProps.verticalPadding.dp)
                .padding(horizontal = kwikBottomIndicatorProps.horizontalPadding.dp)
                .fillMaxWidth(kwikBottomIndicatorProps.width)
                .height(kwikBottomIndicatorProps.height.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = if (selected) listOf(
                            selectedContentColor,
                            selectedContentColor
                        ) else listOf(unselectedContentColor, unselectedContentColor),
                    ),
                    shape = RoundedCornerShape(kwikBottomIndicatorProps.borderRadius.dp)
                ))
        }
    }

}

@Preview
@Composable
private fun KwikBottomTabsWithCounterPreview() {
    val list = listOf(
        KwikTabItem(
            title = "Home",
            icon = Icons.Default.Home,
            content = {

            }
        ),
        KwikTabItem(
            title = "Discover",
            icon = Icons.Default.LocationOn
        ),
        KwikTabItem(
            title = "Favorites",
            icon = Icons.Default.Favorite
        ),
        KwikTabItem(
            title = "Settings",
            icon = Icons.Default.Settings
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        list.size
    }

    KwikTheme {
        KwikBottomTab(
            modifier = Modifier.height(70.dp),
            tabs = list,
            pagerState = pagerState
        )
    }
}
