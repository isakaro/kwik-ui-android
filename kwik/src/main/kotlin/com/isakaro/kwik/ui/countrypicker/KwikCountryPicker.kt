package com.isakaro.kwik.ui.countrypicker

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.utils.KwikCountry
import com.isakaro.kwik.ui.utils.KwikCountryInfo
import com.isakaro.kwik.ui.utils.countryCode
import com.isakaro.kwik.ui.utils.getCountryInfo

/**
 * A country picker component that allows users to select a country from a list.
 *
 * This component displays a button with the selected country's code and dialing code.
 *
 * @param initialCountry the country to be displayed initially. If not provided, the library will try to use the device's country code.
 * If it's also not available, it will default to Rwanda (RW).
 * @param showFlags a flag indicating whether to show country flags or not. Defaults to false.
 * @param countryPicked a callback function that will be triggered when a country is selected. Returns the selected country information [KwikCountryInfo].
 *
 * Usage:
 * ```
 * KwikCountryPicker(
 *     initialCountry = KwikCountry.RW,
 *     title = "Select your country",
 *     countryPicked = { countryInfo ->
 *      // Handle the selected country information
 *     }
 * )
 * */
@Composable
fun KwikCountryPicker(
    initialCountry: KwikCountry? = null,
    showFlags: Boolean = false,
    title: String = "Select your country",
    countryPicked: (KwikCountryInfo) -> Unit
) {

    val context = LocalContext.current
    var showCountryPicker by remember { mutableStateOf(false) }
    var selectedCountryInfo by remember { mutableStateOf(getCountryInfo(initialCountry ?: context.countryCode())) }
    val countryListState = rememberLazyListState()

    KwikCountryPickerDialog(
        title = title,
        open = showCountryPicker,
        countryListState = countryListState,
        onDismiss = {
            showCountryPicker = false
        },
        onSelect = { country ->
            showCountryPicker = false
            selectedCountryInfo = country
            countryPicked(country)
        }
    )

    KwikCountryCodeButton(
        modifier = Modifier.height(55.dp),
        showFlags = showFlags,
        country = selectedCountryInfo
    ){
        showCountryPicker = true
    }

}