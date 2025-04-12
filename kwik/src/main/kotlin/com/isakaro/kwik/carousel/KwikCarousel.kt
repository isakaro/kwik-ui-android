package com.isakaro.kwik.carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.image.KwikImageLoader
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.theme.KwikTheme
import kotlinx.coroutines.launch

/**
 * A generic carousel that displays a list of content in a horizontal scrollable view.
 *
 * @param modifier The modifier to be applied to the carousel (HorizontalPager).
 * @param itemCount The number of items in the carousel.
 * @param initialIndex The index of the item to display first.
 * @param showIndicators Whether to show the page indicators.
 * @param showNavigation Whether to show the navigation buttons.
 * @param showCounter Whether to show the counter.
 * @param onPageIndexChange Callback that is invoked when the current page index changes.
 * @param userScrollEnabled Whether the carousel is scrollable by the user.
 * @param customPrevButton Optional composable to replace the default previous button.
 * @param customNextButton Optional composable to replace the default next button.
 * @param contentBuilder Function that builds the content for each page.
 *
 * Example usage:
 *
 * ```
 * KwikCarousel(
 *      itemCount = 5
 * ){ page ->
 *      // Your content for the specific page
 *      Text("Page $page")
 * }
 * ```
 * */
@Composable
fun KwikCarousel(
    modifier: Modifier = Modifier,
    itemCount: Int,
    initialIndex: Int = 0,
    showIndicators: Boolean = true,
    showNavigation: Boolean = true,
    userScrollEnabled: Boolean = true,
    showCounter: Boolean = false,
    onPageIndexChange: (Int) -> Unit = {},
    customPrevButton: @Composable (() -> Unit)? = null,
    customNextButton: @Composable (() -> Unit)? = null,
    contentBuilder: @Composable (page: Int) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        initialPageOffsetFraction = 0f
    ) {
        itemCount
    }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // Scroll indicators to current page when page changes
    LaunchedEffect(pagerState.currentPage) {
        if (itemCount > 0) {
            listState.scrollToItem(
                index = pagerState.currentPage,
                scrollOffset = -listState.layoutInfo.viewportSize.width / 2
            )
        }
        onPageIndexChange(pagerState.currentPage)
    }

    Box {
        HorizontalPager(
            modifier = modifier,
            state = pagerState,
            userScrollEnabled = userScrollEnabled,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                pagerState,
                Orientation.Horizontal
            ),
            pageContent = { page ->
                contentBuilder(page)
            }
        )

        if (showIndicators && itemCount > 1) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp)
            ) {
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .widthIn(max = this.maxWidth * 3/5)
                        .wrapContentWidth()
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 6.dp)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    userScrollEnabled = false,
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    items(itemCount) { index ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(7.dp)
                                .background(
                                    color = if (pagerState.currentPage == index)
                                        MaterialTheme.colorScheme.primary
                                    else Color.White
                                )
                                .clickable {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                        )
                    }
                }
            }
        }

        if (showNavigation && itemCount > 1) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                if (customPrevButton != null) {
                    Box(modifier = Modifier.clickable {
                        scope.launch {
                            val targetPage = (pagerState.currentPage - 1).coerceAtLeast(0)
                            pagerState.animateScrollToPage(targetPage)
                        }
                    }) {
                        customPrevButton()
                    }
                } else {
                    IconButton(
                        onClick = {
                            scope.launch {
                                val targetPage = (pagerState.currentPage - 1).coerceAtLeast(0)
                                pagerState.animateScrollToPage(targetPage)
                            }
                        },
                        modifier = Modifier
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Previous",
                            tint = Color.White
                        )
                    }
                }
            }

            // Next button
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            ) {
                if (customNextButton != null) {
                    Box(modifier = Modifier.clickable {
                        scope.launch {
                            val targetPage = (pagerState.currentPage + 1).coerceAtMost(itemCount - 1)
                            pagerState.animateScrollToPage(targetPage)
                        }
                    }) {
                        customNextButton()
                    }
                } else {
                    IconButton(
                        onClick = {
                            scope.launch {
                                val targetPage = (pagerState.currentPage + 1).coerceAtMost(itemCount - 1)
                                pagerState.animateScrollToPage(targetPage)
                            }
                        },
                        modifier = Modifier
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        if(showCounter){
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    ).align(Alignment.BottomEnd)
            ) {
                KwikText.TitleSmall(
                    text = "${pagerState.currentPage + 1}/${itemCount}",
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

/**
 * An image carousel that builds on top of [KwikCarousel].
 *
 * @param modifier The modifier to be applied to the carousel.
 * @param images The list of images to display in the carousel.
 * @param initialIndex The index of the item to display first.
 * @param showIndicators Whether to show the page indicators.
 * @param showNavigation Whether to show the navigation buttons.
 * @param userScrollEnabled Whether the carousel is scrollable by the user.
 * @param showCounter Whether to show the counter.
 * */
@Composable
fun KwikImageCarousel(
    modifier: Modifier = Modifier,
    images: List<Any>,
    initialIndex: Int = 0,
    showIndicators: Boolean = true,
    showNavigation: Boolean = true,
    userScrollEnabled: Boolean = true,
    showCounter: Boolean = true
) {
    KwikCarousel(
        modifier = modifier,
        itemCount = images.size,
        initialIndex = initialIndex,
        showIndicators = showIndicators,
        showNavigation = showNavigation,
        showCounter = showCounter,
        userScrollEnabled = userScrollEnabled,
        contentBuilder = { page ->
            KwikImageLoader(
                url = images[page],
                contentScale = ContentScale.Crop
            )
        }
    )
}

@Preview
@Composable
private fun KwikCarouselPreview() {
    KwikTheme {
        KwikCarousel(
            itemCount = 3,
            showNavigation = true,
            contentBuilder = { page ->
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(
                            when (page) {
                                0 -> Color.Red
                                1 -> Color.Green
                                else -> Color.Blue
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    KwikText.TitleSmall(text = "Page ${page + 1}", color = Color.White)
                }
            }
        )
    }
}