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
import com.isakaro.kwik.ui.date.KwikDateFieldButton
import com.isakaro.kwik.ui.date.KwikDatePickerMode
import com.isakaro.kwik.ui.date.KwikDateRangeButton
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
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
        ShowCase(title = "Date Picker with manual input support") {
            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

            KwikDateFieldButton(
                label = "Date of birth",
                placeholder = "Enter your date of birth",
                selected = {
                    selectedDate = it
                }
            )

            KwikVSpacer(8)

            KwikText.BodyMedium(text = "Selected date: ${selectedDate?.toString() ?: "No date selected"}")
        }

        ShowCase(title = "") {
            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

            KwikDateFieldButton(
                label = "Date of birth",
                placeholder = "Enter your date of birth",
                mode = KwikDatePickerMode.Input,
                selected = {
                    selectedDate = it
                }
            )

            KwikVSpacer(8)

            KwikText.BodyMedium(text = "Selected date: ${selectedDate?.toString() ?: "No date selected"}")
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