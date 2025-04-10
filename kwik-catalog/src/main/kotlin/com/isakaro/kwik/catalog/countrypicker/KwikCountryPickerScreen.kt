package com.isakaro.kwik.catalog.countrypicker

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.KwikCountryCodeButton
import com.isakaro.kwik.KwikCountryPickerDialog
import com.isakaro.kwik.KwikText
import com.isakaro.kwik.KwikVSpacer
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.utils.countryList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikCountryPickerScreen(
    navigator: DestinationsNavigator = navigator()
) {

    var showCountryPicker by remember { mutableStateOf(false) }
    var initialCountryInfo by remember { mutableStateOf(countryList.random()) }
    var selectedCountryInfo by remember { mutableStateOf(initialCountryInfo) }
    val countryListState = rememberLazyListState()

    KwikCountryPickerDialog(
        open = showCountryPicker,
        countryListState = countryListState,
        onDismiss = {
            showCountryPicker = false
        },
        onSelect = { country ->
            showCountryPicker = false
            selectedCountryInfo = country
        }
    )

    ShowCaseContainer(
        title = "Date range picker",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Phone number picker") {
            KwikCountryCodeButton(
                modifier = Modifier.height(55.dp),
                country = selectedCountryInfo
            ){
                showCountryPicker = true
            }

            KwikVSpacer(12)

            KwikText.TitleText(
                text = "Selected country: ${selectedCountryInfo.name}"
            )
        }
    }

}