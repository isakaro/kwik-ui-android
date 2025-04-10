package com.isakaro.kwik.catalog.daterangepicker

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikDateField
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(style = SlideInFromRightAnimations::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun KwikDateRangePickerScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Date range picker",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Date Range Picker") {
            KwikDateField(
                label = "Check-in and check-out",
                onDateRangeSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStartScreen() {
    KwikTheme {
        KwikDateRangePickerScreen()
    }
}