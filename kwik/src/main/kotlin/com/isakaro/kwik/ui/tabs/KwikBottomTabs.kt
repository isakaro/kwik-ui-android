package com.isakaro.kwik.ui.tabs

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.helpers.KwikCenterColumn
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.text.KwikText
import kotlinx.coroutines.launch

/**
 * Bottoms tabs component that can display multiple tabs.
 *
 * @param modifier Modifier to be applied to the component
 * @param shape Shape to be applied to the component
 * @param tabs List of [KwikTabItem]tabs to be displayed
 * @param pagerState [PagerState] to be used for the tabs
 * @param elevation Elevation of the component
 * @param containerColor Background color of the component
 * @param selectedContentColor Color of the selected tab
 * @param unselectedContentColor Color of the unselected tab
 * */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun KwikBottomTabs(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    tabs: List<KwikTabItem>,
    pagerState: PagerState,
    elevation: Int = 0,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = Color.Gray
) {
    Scaffold(
        bottomBar = {
            KwikBottomTabView(
                modifier = modifier,
                tabs = tabs,
                shape = shape,
                elevation = elevation,
                pagerState = pagerState,
                containerColor = containerColor,
                selectedContentColor = selectedContentColor,
                unselectedContentColor = unselectedContentColor
            )
        }
    ) { paddingValues ->
        KwikTabsContent(tabs = tabs, pagerState = pagerState)
    }
}

/**
 * The view for the tab row.
 *
 * @param tabs List of tabs to display
 * @param pagerState The state of the pager
 * @param kwikBottomIndicatorProps The properties of the indicator. Refer to [KwikBottomIndicatorProps] for more details.
 * @param containerColor The background color of the tab row
 * @param selectedContentColor The color of the selected tab
 * @param unselectedContentColor The color of the unselected tab
 * @param shape The shape that applies to the entire tab view
 * @param enableIndicator Boolean indicates if the indicator is enabled
 * @param elevation The elevation of the tab view
 * */
@Composable
private fun KwikBottomTabView(
    modifier: Modifier = Modifier,
    tabs: List<KwikTabItem>,
    pagerState: PagerState,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = Color.Gray,
    shape: Shape,
    elevation: Int = 0
) {
    val scope = rememberCoroutineScope()

    Card(
        modifier = modifier.fillMaxWidth().then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation.dp
        ),
        shape = shape
    ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
            tabs.forEachIndexed { index, tab ->
                KwikBottomTabItemView(
                    selected = pagerState.currentPage == index,
                    item = tab,
                    containerColor = containerColor,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor,
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
 * @param item [KwikTabItem] the tab item
 * @param onClick () -> Unit called when the tab is clicked
 *
 * @see KwikTabItem
 * */
@Composable
private fun KwikBottomTabItemView(
    selected: Boolean,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = Color.Gray,
    item: KwikTabItem,
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
                    tint = if(selected) selectedContentColor else unselectedContentColor
                )
            }
            if(item.title != null){
                KwikText.TitleSmall(
                    text = item.title,
                    color = if (selected) selectedContentColor else unselectedContentColor
                )
            }
        }
    }

}

@Preview
@Composable
private fun KwikBottomTabsWithCounterPreview() {
    val navItems = listOf(
        KwikTabItem(
            title = "Home",
            icon = Icons.Default.Home,
            content = {
                KwikCenterColumn {
                    KwikText.BodyMedium(text = "Home")
                }
            }
        ),
        KwikTabItem(
            title = "Discover",
            icon = Icons.Default.LocationOn,
            content = {
                KwikCenterColumn {
                    KwikText.BodyMedium(text = "Discover")
                }
            }
        ),
        KwikTabItem(
            title = "Favorites",
            icon = Icons.Default.Favorite,
            content = {
                KwikCenterColumn {
                    KwikText.BodyMedium(text = "Favorites")
                }
            }
        ),
        KwikTabItem(
            title = "Settings",
            icon = Icons.Default.Settings,
            content = {
                KwikCenterColumn {
                    KwikText.BodyMedium(text = "Settings")
                }
            }
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        navItems.size
    }

    KwikBottomTabs(
        modifier = Modifier.height(70.dp),
        tabs = navItems,
        pagerState = pagerState
    )
}
