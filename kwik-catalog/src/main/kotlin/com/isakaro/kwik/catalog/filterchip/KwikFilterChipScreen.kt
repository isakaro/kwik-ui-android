package com.isakaro.kwik.catalog.filterchip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.ui.filterchip.KwikFilterChipOption
import com.isakaro.kwik.ui.filterchip.KwikFilterChips
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikFilterChipScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ScrollableShowCaseContainer(
        title = "Kwik filter chip",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        val filters = listOf(
            KwikFilterChipOption("Tortuga", 1),
            KwikFilterChipOption("Isla de Muerta", 2),
            KwikFilterChipOption("Davy Jones' Locker", 3),
            KwikFilterChipOption("Shipwreck Cove", 4),
            KwikFilterChipOption("Fountain of Youth", 5)
        )

        ShowCase(
            title = "Kwik filter chip",
        ) {
            var selected by rememberSaveable { mutableStateOf(listOf(filters.first().value)) }

            KwikFilterChips(
                filters = filters,
                preSelection = selected.toSet(),
                filtersUpdated = { selected = it }
            )
        }

        ShowCase(
            title = "Kwik filter chip with multi-selection and hidden checked icon",
        ) {
            var selected by rememberSaveable { mutableStateOf(filters.map { it.value }.take(2)) }

            KwikTheme {
                KwikFilterChips(
                    filters = filters,
                    preSelection = selected.toSet(),
                    filtersUpdated = { selected = it },
                    multiSelection = true,
                    showCheckedIcon = false
                )
            }
        }

        ShowCase(
            title = "Kwik filter chips in flow layout",
        ) {
            var selected by rememberSaveable { mutableStateOf(filters.map { it.value }.take(3)) }

            KwikFilterChips(
                filters = filters,
                flowLayout = true,
                multiSelection = true,
                preSelection = selected.toSet(),
                filtersUpdated = { selected = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikFilterChipScreen() {
    KwikTheme {
        KwikFilterChipScreen()
    }
}
