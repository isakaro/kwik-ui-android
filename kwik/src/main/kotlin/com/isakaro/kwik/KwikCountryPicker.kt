package com.isakaro.kwik

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.utils.CountryInfo
import com.isakaro.kwik.utils.countryList

@Composable
fun KwikCountryPicker(
    initialCountryInfo: CountryInfo = countryList.de,
) {

    var showCountryPicker by remember { mutableStateOf(false) }
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

    KwikCountryCodeButton(
        modifier = Modifier.height(55.dp),
        country = selectedCountryInfo
    ){
        showCountryPicker = true
    }

}