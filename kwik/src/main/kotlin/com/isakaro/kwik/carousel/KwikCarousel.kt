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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.image.KwikImageLoader
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.theme.KwikTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * The state of the carousel
 *
 * @param itemCount: The number of items in the carousel.
 * @param initialIndex: The index of the item to display first.
 * @param currentIndex: The index of the currently displayed item.
 * @param loop: Whether to loop the carousel.
 * */
data class KwikCarouselState(
    val itemCount: Int,
    var initialIndex: Int = 0,
    var currentIndex: Int = initialIndex,
    var loop: Boolean = false
)

@Composable
fun rememberKwikCarouselState(
    state: KwikCarouselState
): MutableState<KwikCarouselState> {
    return remember { mutableStateOf(state) }
}

/**
 * Slide to the next page
 * */
fun MutableState<KwikCarouselState>.next() {
    if(this.value.currentIndex < this.value.itemCount) {
        this.value = KwikCarouselState(
            itemCount = this.value.itemCount,
            initialIndex = this.value.initialIndex,
            currentIndex = if(this.value.currentIndex + 1 >= this.value.itemCount) 0 else this.value.currentIndex + 1
        )
    }
}

/**
 * Slide to the previous page
 * */
fun MutableState<KwikCarouselState>.previous() {
    if(this.value.currentIndex > 0 || this.value.loop) {
        this.value = KwikCarouselState(
            itemCount = this.value.itemCount,
            initialIndex = this.value.initialIndex,
            currentIndex = if(this.value.currentIndex - 1 < 0) this.value.itemCount - 1 else this.value.currentIndex - 1
        )
    }
}

/**
 * A generic carousel that displays a list of content in a horizontal scrollable view.
 *
 * @param modifier The modifier to be applied to the carousel (HorizontalPager).
 * @param shape The shape of the carousel.
 * @param initialIndex The index of the item to display first.
 * @param showIndicators Whether to show the page indicators.
 * @param selectedIndicatorColor The color of the selected page indicator.
 * @param unselectedIndicatorColor The color of the unselected page indicator.
 * @param showNavigation Whether to show the navigation buttons.
 * @param showCounter Whether to show the counter.
 * @param autoPlay Whether to automatically play the carousel.
 * @param autoPlayDelay The delay between auto-play slides in milliseconds.
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
    state: MutableState<KwikCarouselState>,
    shape: Shape = MaterialTheme.shapes.large,
    initialIndex: Int = 0,
    showIndicators: Boolean = true,
    selectedIndicatorColor: Color = Color.White,
    unselectedIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
    indicatorContainerColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
    showNavigation: Boolean = true,
    userScrollEnabled: Boolean = true,
    showCounter: Boolean = false,
    autoPlay: Boolean = false,
    autoPlayDelay: Long = 3000L,
    onPageIndexChange: (Int) -> Unit = {},
    customPrevButton: @Composable (() -> Unit)? = null,
    customNextButton: @Composable (() -> Unit)? = null,
    contentBuilder: @Composable (page: Int) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        initialPageOffsetFraction = 0f
    ) {
        state.value.itemCount
    }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    suspend fun handlePageChange(){
        if (state.value.itemCount > 0) {
            listState.scrollToItem(
                index = pagerState.currentPage,
                scrollOffset = -listState.layoutInfo.viewportSize.width / 2
            )
        }
        onPageIndexChange(pagerState.currentPage)
    }

    // Scroll indicators to current page when page changes
    LaunchedEffect(pagerState.currentPage) {
        handlePageChange()
    }

    LaunchedEffect(state.value) {
        handlePageChange()
    }

    LaunchedEffect(Unit) {
        while (autoPlay) {
            delay(autoPlayDelay)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % state.value.itemCount)
        }
    }

    Box {
        HorizontalPager(
            modifier = modifier.clip(shape),
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

        if (showIndicators && state.value.itemCount > 1) {
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
                            color = indicatorContainerColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 6.dp)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    userScrollEnabled = false,
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    items(state.value.itemCount) { index ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(7.dp)
                                .background(
                                    color = if (pagerState.currentPage == index)
                                        selectedIndicatorColor
                                    else unselectedIndicatorColor
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

        if (showNavigation && state.value.itemCount > 1) {
            if(pagerState.currentPage > 0 || state.value.loop){
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
            }

            if(pagerState.currentPage < state.value.itemCount || state.value.loop){
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    if (customNextButton != null) {
                        Box(modifier = Modifier.clickable {
                            scope.launch {
                                val targetPage = (pagerState.currentPage + 1).coerceAtMost(state.value.itemCount - 1)
                                pagerState.animateScrollToPage(targetPage)
                            }
                        }) {
                            customNextButton()
                        }
                    } else {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    val targetPage = (pagerState.currentPage + 1).coerceAtMost(state.value.itemCount - 1)
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
                    text = "${pagerState.currentPage + 1}/${state.value.itemCount}",
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
 * @param autoPlay Whether to automatically play the carousel.
 * @param autoPlayDelay The delay between auto-play slides in milliseconds.
 * @param onPageIndexChange Callback that is invoked when the current page index changes.
 * */
@Composable
fun KwikImageCarousel(
    modifier: Modifier = Modifier,
    state: MutableState<KwikCarouselState>,
    images: List<Any>,
    initialIndex: Int = 0,
    showIndicators: Boolean = true,
    showNavigation: Boolean = true,
    userScrollEnabled: Boolean = true,
    showCounter: Boolean = true,
    selectedIndicatorColor: Color = Color.White,
    unselectedIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
    indicatorContainerColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
    autoPlay: Boolean = false,
    autoPlayDelay: Long = 3000L,
    onPageIndexChange: (Int) -> Unit = {},
    shape: Shape = MaterialTheme.shapes.large,
    customPrevButton: @Composable (() -> Unit)? = null,
    customNextButton: @Composable (() -> Unit)? = null
) {
    KwikCarousel(
        modifier = modifier,
        state = state,
        shape = shape,
        initialIndex = initialIndex,
        showIndicators = showIndicators,
        showNavigation = showNavigation,
        showCounter = showCounter,
        userScrollEnabled = userScrollEnabled,
        onPageIndexChange = onPageIndexChange,
        autoPlay = autoPlay,
        autoPlayDelay = autoPlayDelay,
        selectedIndicatorColor = selectedIndicatorColor,
        unselectedIndicatorColor = unselectedIndicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        customPrevButton = customPrevButton,
        customNextButton = customNextButton,
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
        val kwikCarouselState = rememberKwikCarouselState(
            KwikCarouselState(
                itemCount = 3,
                initialIndex = 0
            )
        )

        KwikCarousel(
            state = kwikCarouselState,
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