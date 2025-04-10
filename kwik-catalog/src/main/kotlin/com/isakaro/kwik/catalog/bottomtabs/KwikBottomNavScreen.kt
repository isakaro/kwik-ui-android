package com.isakaro.kwik.catalog.bottomtabs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.KwikBottomTab
import com.isakaro.kwik.KwikTabItem
import com.isakaro.kwik.KwikTabsContent
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun KwikBottomTabsScreen(
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

    val navItems = listOf(
        KwikTabItem(
            title = "Home",
            icon = Icons.Rounded.Home,
            content = {
                Content("Home")
            }
        ),
        KwikTabItem(
            title = "Discover",
            icon = Icons.Rounded.LocationOn,
            content = {
                Content("Discover")
            }
        ),
        KwikTabItem(
            title = "Favorites",
            icon = Icons.Rounded.Favorite,
            content = {
                Content("Favorites")
            }
        ),
        KwikTabItem(
            title = "Settings",
            icon = Icons.Rounded.Settings,
            content = {
                Content("Settings")
            }
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        navItems.size
    }

    ShowCaseContainer(
        title = "Bottom Sheet",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        Scaffold(
            content = {
                KwikBottomTab(
                    modifier = Modifier.height(70.dp),
                    tabs = navItems,
                    pagerState = pagerState
                )
                KwikTabsContent(tabs = navItems, pagerState = pagerState)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikBottomTabsScreen() {
    KwikTheme {
        KwikBottomTabsScreen()
    }
}
