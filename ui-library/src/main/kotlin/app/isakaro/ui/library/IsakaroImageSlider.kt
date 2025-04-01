package app.isakaro.ui.library

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.isakaro.ui.library.theme.ColorPrimaryAccent
import com.isakaro.qwik.ui.library.R
import com.isakaro.ui.components.fadingEdge
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListingImageSlider(
    images: List<Any>,
    initialIndex: Int = 0,
    galleryMode: Boolean = false
) {
    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        initialPageOffsetFraction = 0f
    ) {
        images.size
    }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // Scroll indicators to current page when page changes
    LaunchedEffect(pagerState.currentPage) {
        listState.scrollToItem(
            index = pagerState.currentPage,
            scrollOffset = -listState.layoutInfo.viewportSize.width / 2
        )
    }

    Box {
        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            userScrollEnabled = true,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                pagerState,
                Orientation.Horizontal
            ),
            pageContent = { page ->
                IsakaroImageLoader(
                    url = images[page],
                    contentScale = if(galleryMode) ContentScale.Fit else ContentScale.Crop,
                )
            }
        )

        val leftRightFade = Brush.horizontalGradient(0f to Color.Transparent, 0.1f to Color.Black, 0.9f to Color.Black, 1f to Color.Transparent)

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
                    .align(Alignment.Center)
                    .fadingEdge(leftRightFade),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                userScrollEnabled = false,
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                items(images.size) { index ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(7.dp)
                            .background(
                                color = if (pagerState.currentPage == index)
                                    ColorPrimaryAccent
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

        Row(
            modifier = Modifier
                .padding(4.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(12.dp)
                ).align(Alignment.BottomEnd)
        ) {
            Text(
                text = "${pagerState.currentPage + 1}/${images.size}",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewListingImageSlider() {
    ListingImageSlider(
        images = listOf(
            R.drawable.baseline_visibility_24,
            R.drawable.baseline_visibility_24
        )
    )
}