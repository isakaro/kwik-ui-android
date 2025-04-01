package com.isakaro.qwik

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.launch

@Composable
fun QwikTabs(
    tabs: List<IsakaroTabItem>, pagerState: PagerState
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

data class IsakaroTabItem(
    val title: String,
    val icon: Int? = null
)

@Preview
@Composable
fun QwikTabsPreview() {
    val list = listOf(
        IsakaroTabItem(title = "Bite"),
        IsakaroTabItem(title = "Hello"),
        IsakaroTabItem(title = "Bonjour"),
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        list.size
    }

    QwikTabs(
        tabs = list,
        pagerState = pagerState
    )
}
