package com.isakaro.kwik.catalog.counter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.counter.KwikCounter
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
internal fun KwikCounterScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Counter",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Counter with initial value fo 2") {
            var count by remember { mutableIntStateOf(0) }

            KwikCounter(
                initialValue = 2,
                onValueChange = {
                    count = it
                }
            )

            KwikText.BodyMedium(
                text = "Count: $count"
            )
        }

        ShowCase(title = "Counter with limit of 5") {
            var count by remember { mutableIntStateOf(0) }

            KwikCounter(
                initialValue = 0,
                maxValue = 5,
                onValueChange = {
                    count = it
                }
            )

            KwikText.BodyMedium(
                text = "Count: $count"
            )
        }

        ShowCase(title = "Counter with minimum value of 1 and max value of 10") {
            var count by remember { mutableIntStateOf(0) }

            KwikCounter(
                initialValue = 0,
                minValue = 1,
                maxValue = 10,
                onValueChange = {
                    count = it
                }
            )

            KwikText.BodyMedium(
                text = "Count: $count"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikCounterScreen() {
    KwikTheme {
        KwikCounterScreen()
    }
}
