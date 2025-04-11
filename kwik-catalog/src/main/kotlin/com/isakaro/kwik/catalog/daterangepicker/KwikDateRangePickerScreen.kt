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
import java.util.Calendar

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
                label = "Start and end date",
                onDateRangeSelected = {}
            )
        }

        ShowCase(title = "Date Range Picker with mode toggle") {
            KwikDateField(
                label = "Start and end date",
                showModeToggle = true,
                onDateRangeSelected = {}
            )
        }

        ShowCase(title = "Booking date ranger picker") {
            val calendar = Calendar.getInstance()
            val today = calendar.clone() as Calendar

            val sixMonthsAhead = calendar.clone() as Calendar
            sixMonthsAhead.add(Calendar.MONTH, 6)

            KwikDateField(
                label = "Check-in and check-out",
                showModeToggle = true,
                minSelectableDate = today.timeInMillis,
                maxSelectableDate = sixMonthsAhead.timeInMillis,
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