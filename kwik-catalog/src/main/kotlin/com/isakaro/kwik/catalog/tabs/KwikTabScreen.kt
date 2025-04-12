package com.isakaro.kwik.catalog.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.isakaro.kwik.tabs.KwikHorizontalTab
import com.isakaro.kwik.tabs.KwikTabItem
import com.isakaro.kwik.tabs.KwikTabsContent
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikTabScreen(
    navigator: DestinationsNavigator = navigator()
) {

    @Composable
    fun Content(text: String) {
        Column {
            repeat(5) {
                Text(text = text)
            }
        }
    }

    val tabsWithIcons = listOf(
        KwikTabItem(
            title = "Muraho",
            icon = Icons.Default.Call
        ){
            Content(text = "Muraho")
        },
        KwikTabItem(
            title = "Hello",
            icon = Icons.Default.MailOutline
        ){
            Content(text = "Hello")
        },
        KwikTabItem(
            title = "Jambo",
            icon = Icons.Default.Notifications
        ){
            Content(text = "Jambo")
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
                Content(text = "Muraho")
            }
        ),
        KwikTabItem(
            title = "Hello",
            content = {
                Content(text = "Hello")
            }
        ),
        KwikTabItem(
            title = "Jambo",
            content = {
                Content(text = "Jambo")
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
            Content(text = "Muraho")
        },
        KwikTabItem(
            title = "Hello",
        ){
            Content(text = "Hello")
        },
        KwikTabItem(
            title = "Jambo",
            counter = 1
        ){
            Content(text = "Jambo")
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

