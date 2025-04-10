package com.isakaro.kwik

import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.telephony.TelephonyManager
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
import com.isakaro.kwik.utils.KwikCountry
import com.isakaro.kwik.utils.countryCode
import com.isakaro.kwik.utils.getCountryInfo

/**
 * A country picker component that allows users to select a country from a list.
 *
 * This component displays a button with the selected country's code and dialing code.
 *
 * @param initialCountry the country to be displayed initially. If not provided, the library will try to use the device's country code.
 * If it's also not available, it will default to Rwanda (RW).
 * */
@Composable
fun KwikCountryPicker(
    initialCountry: KwikCountry? = null
) {

    val context = LocalContext.current
    var showCountryPicker by remember { mutableStateOf(false) }
    var selectedCountryInfo by remember { mutableStateOf<KwikCountry>(initialCountry ?: getCountryInfo(context.countryCode())) }
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

    KwikCountryCodeButton(
        modifier = Modifier.height(55.dp),
        country = selectedCountryInfo
    ){
        showCountryPicker = true
    }

}