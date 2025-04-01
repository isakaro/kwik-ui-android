package com.isakaro.qwik.catalog.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.isakaro.appcatalog.R
import com.isakaro.qwik.catalog.common.ShowCase
import com.isakaro.qwik.catalog.common.ShowCaseContainer
import com.isakaro.qwik.catalog.tabs.AmpersandTabs.AmpersandTabs

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TabScreen() {
    val tabs = listOf(
        AmpersandTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner),
        AmpersandTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner),
        AmpersandTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner)
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabs.size
    }

    val tabsWithCounter = listOf(
        AmpersandTabs.TabItem(R.string.app_name, 3),
        AmpersandTabs.TabItem(R.string.app_name, 0),
        AmpersandTabs.TabItem(R.string.app_name, 55)
    )

    val pagerState2 = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabsWithCounter.size
    }

    ShowCaseContainer {
        ShowCase(title = "Tabs with icons") {
            AmpersandTabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
        ShowCase(title = "Tabs without icons") {
            AmpersandTabs(
                tabs = tabsWithCounter,
                pagerState = pagerState2,
                withIcons = false
            )
            TabsContent(tabs = tabsWithCounter, pagerState = pagerState2)
        }
    }
}

