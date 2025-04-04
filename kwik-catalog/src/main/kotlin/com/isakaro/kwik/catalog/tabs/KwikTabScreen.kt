package com.isakaro.kwik.catalog.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.isakaro.Kwik.catalog.R
import com.isakaro.kwik.KwikHorizontalTab
import com.isakaro.kwik.KwikTabItem
import com.isakaro.kwik.KwikTabsContent
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun KwikTabScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val tabsWithIcons = listOf(
        KwikTabItem(
            title = "Muraho",
            icon = R.drawable.qr_code_scanner
        ){
            Column {
                repeat(5) {
                    Text(text = "Muraho")
                }
            }
        },
        KwikTabItem(
            title = "Hello",
            icon = R.drawable.qr_code_scanner
        ){
            Column {
                repeat(5) {
                    Text(text = "Hello")
                }
            }
        },
        KwikTabItem(
            title = "Jambo",
            icon = R.drawable.qr_code_scanner
        ){
            Column {
                repeat(5) {
                    Text(text = "Jambo")
                }
            }
        }
    )

    val pagerStateWithIcons = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabsWithIcons.size
    }

    val tabsWithoutIcons = listOf(
        KwikTabItem(
            title = "Muraho",
            content = {
                Text(text = "Muraho")
            }
        ),
        KwikTabItem(
            title = "Hello",
            content = {
                Text(text = "Hello")
            }
        ),
        KwikTabItem(
            title = "Jambo",
            content = {
                Text(text = "Jambo")
            }
        )
    )

    val pagerStateWithoutIcons = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabsWithoutIcons.size
    }

    val tabsWithCounters = listOf(
        KwikTabItem(
            title = "Muraho",
            counter = 3
        ){
            Text(text = "Muraho")
        },
        KwikTabItem(
            title = "Hello",
        ){
            Text(text = "Hello")
        },
        KwikTabItem(
            title = "Jambo",
            counter = 1
        ){
            Text(text = "Jambo")
        }
    )

    val pagerStateWithCounters = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabsWithCounters.size
    }

    ShowCaseContainer(
        title = "Tabs",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Tabs with icons") {
            KwikHorizontalTab(
                tabs = tabsWithIcons,
                pagerState = pagerStateWithIcons
            )
            KwikTabsContent(tabs = tabsWithIcons, pagerState = pagerStateWithIcons)
        }
        ShowCase(title = "Tabs without icons") {
            KwikHorizontalTab(
                tabs = tabsWithoutIcons,
                pagerState = pagerStateWithoutIcons
            )
            KwikTabsContent(tabs = tabsWithoutIcons, pagerState = pagerStateWithoutIcons)
        }
        ShowCase(title = "Tabs with counters") {
            KwikHorizontalTab(
                tabs = tabsWithCounters,
                pagerState = pagerStateWithCounters
            )
            KwikTabsContent(tabs = tabsWithCounters, pagerState = pagerStateWithCounters)
        }
    }
}

