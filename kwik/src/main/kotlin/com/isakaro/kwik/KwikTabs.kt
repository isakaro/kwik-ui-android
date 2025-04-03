package com.isakaro.kwik

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.launch

@Composable
fun KwikTabs(
    tabs: List<KwikTabItem>, pagerState: PagerState
) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = Color.White,
        contentColor = Color.Black,
        divider = { },
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .height(4.dp)
                    .padding(horizontal = 28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = MaterialTheme.colorScheme.primary)
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = pagerState.currentPage == index,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = Color.LightGray,
                text = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        if(tab.icon != null) {
                            Icon(
                                painter = painterResource(id = tab.icon),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(25.dp).padding(end = 8.dp)
                            )
                        }
                        Text(text = tab.title)
                    }
                },
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                })
        }
    }
}

data class KwikTabItem(
    val title: String,
    val icon: Int? = null
)

@Preview
@Composable
private fun KwikTabsPreview() {
    val list = listOf(
        KwikTabItem(title = "Bite"),
        KwikTabItem(title = "Hello"),
        KwikTabItem(title = "Bonjour"),
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        list.size
    }

    KwikTabs(
        tabs = list,
        pagerState = pagerState
    )
}

/**
 * A tab component that displays a list of tabs with icons and counters as optional features
 * The contents must be passed as a separate composable because they are defined by the user depending on the use case
 * */
object KwikTabs {

    /**
     * @param title The title of the tab
     * @param counter The counter to be displayed on the tab
     * @param icon The icon to be displayed on the tab
     * */
    data class KwikTabItem(@StringRes val title: Int, val counter: Int = 0, @DrawableRes val icon: Int? = null)

    @Composable
    fun KwikTabs(tabs: List<KwikTabItem>, pagerState: PagerState, withIcons: Boolean = true) {
        val scope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color.Black)
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                KwikTabItem(selected = pagerState.currentPage == index, item = tab, withIcons){
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            }
        }
    }

}

@Composable
fun KwikTabsContent(
    tabs: List<KwikTabs.KwikTabItem>,
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = Modifier,
        state = pagerState,
        pageSpacing = 0.dp,
        userScrollEnabled = true,
        reverseLayout = false,
        contentPadding = PaddingValues(0.dp),
        pageSize = PageSize.Fill,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
        key = null,
        pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
            pagerState,
            Orientation.Horizontal
        ),
        pageContent = { page ->
            when (page) {

            }
        }
    )
}

@Composable
fun KwikTabItem(selected: Boolean, item: KwikTabs.KwikTabItem, withIcons: Boolean, onClick: () -> Unit){
    Box(
        modifier = Modifier
    ) {
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
            .height(70.dp)
            .padding(top = 8.dp)
            .clickable(
                onClick = { onClick() },
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            if(item.icon != null && withIcons){
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = stringResource(id = item.title),
                    tint = if(selected) Color.Black else Color.Gray,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Text(
                    text = stringResource(id = item.title),
                    style = MaterialTheme.typography.titleSmall,
                    color = if(selected) Color.Black else Color.Gray,
                )
                if(item.counter > 0){
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(modifier = Modifier
                        .size(if (item.counter > 9) 30.dp else 25.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) Color.Black else Color.Gray)
                        .padding(4.dp),
                        contentAlignment = Alignment.Center,
                    ){
                        Text(
                            text = if(item.counter > 99) "99+" else item.counter.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = if(selected) Color.White else Color.LightGray
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier
            .align(Alignment.BottomCenter)
            .width(80.dp)
            .height(4.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = if (selected) listOf(
                        Color.Black,
                        Color.Black
                    ) else listOf(Color.Transparent, Color.Transparent),
                )
            ))
    }

}
