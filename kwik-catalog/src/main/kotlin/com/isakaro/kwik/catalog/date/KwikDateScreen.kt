package com.isakaro.kwik.catalog.date

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.date.KwikDateField
import com.isakaro.kwik.date.KwikDatePickerMode
import com.isakaro.kwik.date.KwikDateRangeButton
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(style = SlideInFromRightAnimations::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun KwikDateScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ScrollableShowCaseContainer(
        title = "Date range picker",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Date Picker") {
            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

            KwikDateField(
                label = "Birthday",
                placeholder = "Select your birthday",
                selected = {
                    selectedDate = it
                }
            )
        }

        ShowCase(title = "Date picker in manual edit mode") {
            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

            KwikDateField(
                label = "Birthday",
                placeholder = "Select your birthday",
                mode = KwikDatePickerMode.Edit,
                selected = {
                    selectedDate = it
                }
            )
        }

        ShowCase(title = "Date Range Picker") {
            KwikDateRangeButton(
                label = "Start and end date",
                onDateRangeSelected = {}
            )
        }

        ShowCase(title = "Date Range Picker with mode toggle") {
            KwikDateRangeButton(
                label = "Start and end date",
                showModeToggle = true,
                onDateRangeSelected = {}
            )
        }

        ShowCase(title = "Booking date ranger picker") {
            val today = LocalDate.now()
            val zoneId = ZoneId.systemDefault()
            val sixMonthsAhead = today.plusMonths(6)

            KwikDateRangeButton(
                label = "Check-in and check-out",
                showModeToggle = true,
                minSelectableDate = today.atStartOfDay(zoneId).toInstant().toEpochMilli(),
                maxSelectableDate = sixMonthsAhead.atStartOfDay(zoneId).toInstant().toEpochMilli(),
                onDateRangeSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikDateScreen() {
    KwikTheme {
        KwikDateScreen()
    }
}