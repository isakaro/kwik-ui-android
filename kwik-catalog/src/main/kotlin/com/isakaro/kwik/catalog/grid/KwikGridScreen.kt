package com.isakaro.kwik.catalog.grid

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.card.KwikImageCard
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.utils.KwikConstants
import com.isakaro.kwik.grid.KwikDiv
import com.isakaro.kwik.grid.KwikGrid
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
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
                rowSpan = 0,
                colPosition = 0,
                rowPosition = 0,
                onClick = {},
                content = {
                    KwikImageCard(
                        image = KwikConstants.SAMPLE_IMAGE
                    )
                }
            ),
            KwikDiv(
                colSpan = 1,
                rowSpan = 1,
                colPosition = 3,
                rowPosition = 0,
                onClick = {},
                content = {
                    KwikImageCard(
                        image = KwikConstants.SAMPLE_IMAGE
                    )
                }
            )

        )

        ShowCase {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                KwikGrid(
                    cols = 4,
                    rows = 4,
                    gap = 4,
                    items = items
                )
            }
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
