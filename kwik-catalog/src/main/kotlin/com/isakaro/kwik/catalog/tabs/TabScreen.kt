package com.isakaro.kwik.catalog.tabs

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.catalog.tabs.KwikTabs.KwikTabs
import com.isakaro.kwik.navigator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun TabScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val tabs = listOf(
        KwikTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner),
        KwikTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner),
        KwikTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner)
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabs.size
    }

    val tabsWithCounter = listOf(
        KwikTabs.TabItem(R.string.app_name, 3),
        KwikTabs.TabItem(R.string.app_name, 0),
        KwikTabs.TabItem(R.string.app_name, 55)
    )

    val pagerState2 = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabsWithCounter.size
    }

    ShowCaseContainer(
        title = "Tabs",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Tabs with icons") {
            KwikTabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
        ShowCase(title = "Tabs without icons") {
            KwikTabs(
                tabs = tabsWithCounter,
                pagerState = pagerState2,
                withIcons = false
            )
            TabsContent(tabs = tabsWithCounter, pagerState = pagerState2)
        }
    }
}

