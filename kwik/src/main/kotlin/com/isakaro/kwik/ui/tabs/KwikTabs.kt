package com.isakaro.kwik.ui.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.spacer.KwikHSpacer
import com.isakaro.kwik.ui.text.KwikText
import kotlinx.coroutines.launch

/**
 * The properties of a tab item.
 * 
 * @param title The title of the tab
 * @param counter The counter to display on the tab
 * @param icon The icon to display on the tab. Can be a drawable resource, a vector resource or a url
 * @param content The content to display when the tab is selected
 * */
data class KwikTabItem(
    val title: String? = null,
    val counter: Int = 0,
    val icon: Any? = null,
    val content: @Composable () -> Unit
)

/**
* Properties for the indicator.
 * @param height The height of the indicator
 * @param width The width of the indicator. It's a percentage of the screen width. For example, if the width is 0.8, the indicator will be 80% of the screen width.
 * @param horizontalPadding The horizontal padding of the indicator
 * @param verticalPadding The vertical padding of the indicator
 * @param borderRadius The border radius of the indicator
* */
data class KwikIndicatorProps(
    val height: Float = 4f,
    val width: Float = 0.8f,
    val horizontalPadding: Float = 0f,
    val verticalPadding: Float = 0f,
    val borderRadius: Float = 4f
)

/**
 * A versatile horizontal tab pager capable of displaying any content.
 *
 * @param tabs List of tabs to display
 * @param pagerState The state of the pager
 * @param kwikIndicatorProps The properties of the indicator. Refer to [KwikIndicatorProps] for more details.
 * @param containerColor The background color of the tab row
 * @param indicatorColor The color of the indicator
 * @param divider The divider to display between tabs
 * @param selectedContentColor The color of the selected tab
 * */
@Composable
internal fun KwikHorizontalTab(
    tabs: List<KwikTabItem>, pagerState: PagerState,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    kwikIndicatorProps: KwikIndicatorProps = KwikIndicatorProps(),
    divider: @Composable () -> Unit = {},
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = Color.Gray
) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = containerColor,
        divider = divider,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .height(kwikIndicatorProps.height.dp)
                    .padding(vertical = kwikIndicatorProps.verticalPadding.dp)
                    .padding(horizontal = kwikIndicatorProps.horizontalPadding.dp)
                    .clip(RoundedCornerShape(kwikIndicatorProps.borderRadius.dp))
                    .background(color = indicatorColor)
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            KwikTabItemView(
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

@Composable
fun KwikTabsContent(
    modifier: Modifier = Modifier,
    tabs: List<KwikTabItem>,
    pagerState: PagerState,
    userScrollEnabled: Boolean = true,
    reverseLayout: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        pageSpacing = 0.dp,
        userScrollEnabled = userScrollEnabled,
        reverseLayout = reverseLayout,
        contentPadding = contentPadding,
        pageSize = pageSize,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
        pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
            pagerState,
            Orientation.Horizontal
        ),
        pageContent = { page ->
            tabs[page].content()
        }
    )
}

@Composable
fun KwikTabItemView(
    selected: Boolean,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = Color.Gray,
    item: KwikTabItem,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize().background(containerColor)
    ) {
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
            .heightIn(45.dp)
            .clickable(
                onClick = { onClick() },
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(item.icon != null){
                KwikImageView(
                    modifier = Modifier.size(30.dp),
                    url = item.icon,
                    tint = if(selected) selectedContentColor else unselectedContentColor
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                if(item.title != null){
                    KwikText.TitleSmall(
                        text = item.title,
                        color = if (selected) selectedContentColor else unselectedContentColor,
                    )
                }
                if(item.counter > 0){
                    KwikHSpacer(6)
                    Box(modifier = Modifier
                        .size(if (item.counter > 9) 30.dp else 25.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) selectedContentColor else unselectedContentColor)
                        .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ){
                        KwikText.BodySmall(
                            text = if (item.counter > 99) "99+" else item.counter.toString(),
                            color = if (selected) Color.White else Color.LightGray
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
            .align(Alignment.BottomCenter)
            .width(80.dp)
            .height(4.dp)
        )
    }

}

/**
 * A versatile horizontal tab pager capable of displaying any content. Can be used independently or with a pager.
 *
 * @param tabs List of tabs to display
 * @param pagerState The state of the pager
 * @param kwikIndicatorProps The properties of the indicator. Refer to [KwikIndicatorProps] for more details.
 * @param containerColor The background color of the tab row
 * @param indicatorColor The color of the indicator
 * @param divider The divider to display between tabs
 * @param selectedContentColor The color of the selected tab
 * @param unselectedContentColor The color of the unselected tab
 *
 * Usage example:
 * ```
 * val list = listOf(
 *         KwikTabItem(
 *             title = "Muraho",
 *             content = {
 *                 Text(text = "Muraho")
 *             }
 *         ),
 *         KwikTabItem(
 *             title = "Hello",
 *             content = {
 *                 Text(text = "Hello")
 *             }
 *         ),
 *         KwikTabItem(
 *             title = "Jambo",
 *             content = {
 *                 Text(text = "Jambo")
 *             }
 *         )
 *     )
 *
 *     val pagerState = rememberPagerState(
 *         initialPage = 0,
 *         initialPageOffsetFraction = 0f
 *     ) {
 *         list.size
 *     }
 *
 *     KwikHorizontalTab(
 *         tabs = list,
 *         pagerState = pagerState
 *     )
 *     ```
 * */
@Composable
fun KwikHorizontalTabs(
    tabs: List<KwikTabItem>, pagerState: PagerState,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    kwikIndicatorProps: KwikIndicatorProps = KwikIndicatorProps(),
    divider: @Composable () -> Unit = {},
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = Color.Gray
){
    Column {
        KwikHorizontalTab(
            tabs = tabs,
            pagerState = pagerState,
            containerColor = containerColor,
            indicatorColor = indicatorColor,
            kwikIndicatorProps = kwikIndicatorProps,
            divider = divider,
            selectedContentColor = selectedContentColor,
            unselectedContentColor = unselectedContentColor
        )
        KwikTabsContent(
            tabs = tabs,
            pagerState = pagerState
        )
    }
}

@Preview
@Composable
private fun KwikTabsPreview() {
    val list = listOf(
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

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        list.size
    }

    KwikHorizontalTab(
        tabs = list,
        pagerState = pagerState
    )
}

@Preview
@Composable
private fun KwikTabsWithCounterPreview() {
    val list = listOf(
        KwikTabItem(
            title = "Muraho",
            counter = 3,
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
            counter = 1,
            content = {
                Text(text = "Jambo")
            }
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        list.size
    }

    KwikHorizontalTabs(
        tabs = list,
        pagerState = pagerState
    )
}
