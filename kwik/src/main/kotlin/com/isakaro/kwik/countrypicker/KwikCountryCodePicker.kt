package com.isakaro.kwik.countrypicker

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.dialog.KwikDialog
import com.isakaro.kwik.spacer.KwikHSpacer
import com.isakaro.kwik.spacer.KwikVSpacer
import com.isakaro.kwik.textfield.KwikSearchView
import com.isakaro.kwik.textfield.kwikTextFieldColors
import com.isakaro.kwik.theme.KwikTheme
import com.isakaro.kwik.utils.KwikCountry
import com.isakaro.kwik.utils.KwikCountryInfo
import com.isakaro.kwik.utils.countryList
import com.isakaro.kwik.utils.resolveCountries
import com.isakaro.kwik.utils.text

@Composable
fun KwikCountryCodePicker(
    state: LazyListState,
    includeOnlyCountries: List<KwikCountry> = emptyList(),
    omitCountries: List<KwikCountry> = emptyList(),
    enableSearch: Boolean = true,
    showFlags: Boolean = true,
    noCountryFoundMessage: String = "No country found",
    onSelect: (KwikCountryInfo) -> Unit
){
    val countries = remember { resolveCountries(includeOnly = includeOnlyCountries, omit = omitCountries) }
    val searchResults = remember { mutableStateOf(countries) }
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if(enableSearch && countries.isNotEmpty()){
            KwikSearchView(
                state = searchQuery,
                colors = kwikTextFieldColors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                onTextChange = { query ->
                    if (query.isBlank()) {
                        searchResults.value = countries
                    }
                    searchResults.value = countries.filter { country ->
                        val searchTerm = query.trim().lowercase()
                        val tags = country.tags.joinToString(" ") { it.lowercase() }
                        country.name.lowercase().contains(searchTerm) || country.dialingCode.contains(searchTerm) || tags.contains(searchTerm)
                    }
                },
                onTextCleared = {
                    searchResults.value = countries
                }
            )
        }

        if (searchResults.value.isEmpty() && searchQuery.text.isNotEmpty()) {
            KwikText.TitleMedium(
                text = noCountryFoundMessage,
                modifier = Modifier.padding(bottom = 100.dp)
            )
        }

        LazyColumn(state = state, modifier = Modifier.fillMaxSize().weight(1f)) {
            itemsIndexed(searchResults.value) { index, country ->
                CountryCodeItem(
                    country = country,
                    showFlags = showFlags
                ){
                    onSelect(country)
                }
            }
        }

    }
}

@Composable
fun KwikCountryPickerDialog(
    open: Boolean,
    title: String = "Where are you from?",
    countryListState: LazyListState,
    onSelect: (KwikCountryInfo) -> Unit,
    onDismiss: () -> Unit
){
    KwikDialog.ContentDialog(
        open = open,
        title = title,
        withCloseIcon = true,
        dismiss = { onDismiss() }
    ) {
        KwikCountryCodePicker(
            state = countryListState,
            onSelect = { onSelect(it) },
        )
    }
}

@Composable
fun KwikCountryCodeButton(
    modifier: Modifier = Modifier,
    showFlags: Boolean = false,
    country: KwikCountryInfo?,
    enabled: Boolean = true,
    onClick: () -> Unit
){
    Button(
        onClick = { onClick() },
        modifier = modifier.alpha(if (enabled) 1.5f else 0.5f),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(8.dp),
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if(showFlags && country != null){
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 10.dp),
                    painter = painterResource(id = country.flag),
                    contentDescription = country.name
                )
            }
            KwikText.TitleMedium(
                text = country?.code ?: "",
                modifier = Modifier
            )
            KwikText.TitleMedium(
                text = country?.dialingCode ?: ""
            )
            Icon(
                Icons.Filled.KeyboardArrowDown,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(35.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
fun CountryCodeItem(
    country: KwikCountryInfo,
    showFlags: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClick()
            }) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.Center)) {
            if(showFlags){
                KwikHSpacer(10)

                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 10.dp),
                    painter = painterResource(id = country.flag),
                    contentDescription = country.name
                )
            }

            KwikHSpacer(10)

            KwikText.BodyMedium(
                modifier = Modifier.weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = country.name
            )

            KwikHSpacer(10)

            KwikText.BodyMedium(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = country.dialingCode,
                modifier = Modifier.padding(end = 10.dp),
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun KwikCountryCodePickerPreview(){
    KwikTheme {
        KwikCountryCodePicker(
            state = rememberLazyListState(),
            onSelect = {},
        )
    }
}

@Preview
@Composable
private fun KwikCountryCodeButtonPreview(){
    KwikTheme {
        KwikCountryCodeButton(
            country = countryList.random(),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun KwikCountryCodeButtonWithFlagsPreview(){
    KwikTheme {
        KwikCountryCodeButton(
            country = countryList.random(),
            showFlags = true,
            onClick = {}
        )
    }
}