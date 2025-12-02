package com.isakaro.kwik.catalog.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.utils.KwikConstants
import com.isakaro.kwik.ui.grid.KwikDiv
import com.isakaro.kwik.ui.grid.KwikGrid
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
fun KwikGridScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ScrollableShowCaseContainer(
        title = "Grid",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {

        val items = listOf(
            KwikDiv(
                colSpan = 3,
                colPosition = 0,
                rowPosition = 0,
                onClick = {},
                content = {
                    KwikImageView(
                        modifier = Modifier.background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                        url = KwikConstants.SAMPLE_IMAGE
                    )
                }
            ),
            KwikDiv(
                colPosition = 3,
                rowPosition = 0,
                onClick = {},
                content = {
                    KwikImageView(
                        modifier = Modifier.background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                        url = KwikConstants.SAMPLE_IMAGE
                    )
                }
            ),
            KwikDiv(
                colPosition = 0,
                rowPosition = 1,
                onClick = {},
                content = {
                    KwikImageView(
                        modifier = Modifier.background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                        url = KwikConstants.SAMPLE_IMAGE
                    )
                }
            ),
            KwikDiv(
                colPosition = 1,
                rowPosition = 1,
                onClick = {},
                content = {
                    KwikImageView(
                        modifier = Modifier.background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                        url = KwikConstants.SAMPLE_IMAGE
                    )
                }
            ),
            KwikDiv(
                colPosition = 0,
                rowPosition = 2,
                colSpan = 2,
                onClick = {},
                content = {
                    KwikImageView(
                        modifier = Modifier.background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                        url = KwikConstants.SAMPLE_IMAGE
                    )
                }
            ),
            KwikDiv(
                colPosition = 2,
                rowPosition = 1,
                colSpan = 2,
                rowSpan = 2,
                onClick = {},
                content = {
                    KwikImageView(
                        modifier = Modifier.background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                        url = KwikConstants.SAMPLE_IMAGE
                    )
                }
            )
        )

        ShowCase {
            KwikGrid(
                modifier = Modifier.fillMaxSize().height(300.dp),
                cols = 4,
                rows = 3,
                gap = 4,
                items = items
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikGridScreen() {
    KwikTheme {
        KwikGridScreen()
    }
}
