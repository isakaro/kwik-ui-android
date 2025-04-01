package com.isakaro.qwik.catalog.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.isakaro.appcatalog.R
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.catalog.tabs.QwikTabs.QwikTabs

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TabScreen() {
    val tabs = listOf(
        QwikTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner),
        QwikTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner),
        QwikTabs.TabItem(R.string.app_name, R.drawable.qr_code_scanner)
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabs.size
    }

    val tabsWithCounter = listOf(
        QwikTabs.TabItem(R.string.app_name, 3),
        QwikTabs.TabItem(R.string.app_name, 0),
        QwikTabs.TabItem(R.string.app_name, 55)
    )

    val pagerState2 = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabsWithCounter.size
    }

    ShowCaseContainer {
        ShowCase(title = "Tabs with icons") {
            QwikTabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
        ShowCase(title = "Tabs without icons") {
            QwikTabs(
                tabs = tabsWithCounter,
                pagerState = pagerState2,
                withIcons = false
            )
            TabsContent(tabs = tabsWithCounter, pagerState = pagerState2)
        }
    }
}

