package com.isakaro.kwik.catalog.countrypicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.isakaro.kwik.ui.countrypicker.KwikCountryPicker
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.utils.KwikCountry
import com.isakaro.kwik.ui.utils.KwikCountryInfo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
internal fun KwikCountryPickerScreen(
    navigator: DestinationsNavigator = navigator()
) {

    var showCountryPicker by remember { mutableStateOf(false) }

    ShowCaseContainer(
        title = "Country picker",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Country code picker") {
            var selectedCountryInfo by remember { mutableStateOf<KwikCountryInfo?>(null) }

            KwikCountryPicker { country ->
                showCountryPicker = true
                selectedCountryInfo = country
            }

            KwikVSpacer(12)

            if(selectedCountryInfo != null){
                KwikText.BodyMedium(
                    text = buildAnnotatedString {
                        append("Selected country: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                            append(selectedCountryInfo?.name ?: "")
                        }
                    }
                )
            }
        }

        ShowCase(title = "Country code picker with flag") {
            var selectedCountryInfo by remember { mutableStateOf<KwikCountryInfo?>(null) }

            KwikCountryPicker(
                showFlags = true
            ) { country ->
                showCountryPicker = true
                selectedCountryInfo = country
            }

            KwikVSpacer(12)

            if(selectedCountryInfo != null){
                KwikText.BodyMedium(
                    text = buildAnnotatedString {
                        append("Selected country: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                            append(selectedCountryInfo?.name ?: "")
                        }
                    }
                )
            }
        }

        ShowCase(title = "Country code picker with initial country") {
            var selectedCountryInfo by remember { mutableStateOf<KwikCountryInfo?>(null) }

            KwikCountryPicker(
                initialCountry = KwikCountry.RW
            ) { country ->
                showCountryPicker = true
                selectedCountryInfo = country
            }

            KwikVSpacer(12)

            if(selectedCountryInfo != null){
                KwikText.BodyMedium(
                    text = buildAnnotatedString {
                        append("Selected country: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                            append(selectedCountryInfo?.name ?: "")
                        }
                    }
                )
            }
        }
    }

}