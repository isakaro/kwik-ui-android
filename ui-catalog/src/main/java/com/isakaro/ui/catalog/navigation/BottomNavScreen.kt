package com.isakaro.ui.catalog.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.ui.catalog.common.ShowCase
import com.isakaro.ui.catalog.common.ShowCaseContainer
import app.isakaro.ui.library.theme.PrimaryColor
import app.isakaro.ui.library.theme.Theme.AmpersandTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun BottomNavScreen() {
    var selectedScreenRoute by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        navItems.size
    }

    ShowCaseContainer {
        ShowCase(title = "Bottom Navigation") {
            var selectedDrawerItem by remember { mutableStateOf(0) }
            val items = listOf("Songs", "Artists", "Playlists")

            Scaffold(
                bottomBar = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = PrimaryColor
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 20.dp
                        )
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
                            navItems.forEachIndexed { index, navItem ->
                                BottomNavigationItem(
                                    selected = navItem.route == selectedScreenRoute,
                                    item = navItem,
                                    onClick = {
                                        selectedScreenRoute = navItem.route
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }
                                )
                            }
                        }
                    }
                },
                content = {
                    // Screen content
                }
            )
        }
    }
}

@Composable
fun BottomNavigationItem(selected: Boolean, item: BottomNavItem, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 8.dp)
            .clickable(
                onClick = { onClick() },
            ),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.name,
                tint = if(selected) Color.Black else Color.Gray,
                modifier = Modifier
                    .size(30.dp)
            )
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleSmall,
                color = if(selected) Color.Black else Color.Gray
            )
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

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)

val navItems = listOf(
    BottomNavItem(
        name = "Swaps",
        route = "swaps",
        icon = Icons.Rounded.Refresh,
    ),
    BottomNavItem(
        name = "Stations",
        route = "station",
        icon = Icons.Rounded.LocationOn,
    ),
    BottomNavItem(
        name = "Bikes",
        route = "bikes",
        icon = Icons.Rounded.ShoppingCart,
    ),
    BottomNavItem(
        name = "Settings",
        route = "settings",
        icon = Icons.Rounded.Settings,
    ),
)

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        BottomNavScreen()
    }
}
