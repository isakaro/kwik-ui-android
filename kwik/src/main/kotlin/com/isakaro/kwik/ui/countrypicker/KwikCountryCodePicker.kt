package com.isakaro.kwik.ui.countrypicker

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.dialog.KwikDialog
import com.isakaro.kwik.ui.spacer.KwikHSpacer
import com.isakaro.kwik.ui.inputfields.KwikSearchView
import com.isakaro.kwik.ui.inputfields.kwikTextFieldColors
import com.isakaro.kwik.ui.utils.KwikCountry
import com.isakaro.kwik.ui.utils.KwikCountryInfo
import com.isakaro.kwik.ui.utils.countryList
import com.isakaro.kwik.ui.utils.resolveCountries
import com.isakaro.kwik.ui.utils.text

@Composable
fun KwikCountryCodePicker(
    state: LazyListState,
    includeOnlyCountries: List<KwikCountry> = emptyList(),
    omitCountries: List<KwikCountry> = emptyList(),
    enableSearch: Boolean = true,
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
                modifier = Modifier.height(65.dp),
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
                KwikCountryCodeItem(
                    country = country
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
    title: String,
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
    showCountryCode: Boolean = false,
    showDialingCode: Boolean = true,
    country: KwikCountryInfo?,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
){
    Button(
        onClick = { onClick() },
        modifier = modifier.alpha(if (enabled) 1.5f else 0.5f).padding(horizontal = 4.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
        contentPadding = PaddingValues(8.dp),
        shape = shape,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if(showFlags && country != null){
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = country.flag),
                    contentDescription = country.name
                )
            }
            if(showCountryCode){
                KwikText.TitleMedium(
                    text = country?.code?.name ?: ""
                )
            }
            if(showDialingCode){
                KwikText.TitleMedium(
                    text = country?.dialingCode ?: ""
                )
            }
            if(enabled){
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(35.dp),
                    contentDescription = "Select country code"
                )
            }
        }
    }
}

@Composable
fun KwikCountryCodeItem(
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
    KwikCountryCodePicker(
        state = rememberLazyListState(),
        onSelect = {

        },
    )
}

@Preview
@Composable
private fun KwikCountryCodeButtonPreview(){
    KwikCountryCodeButton(
        country = countryList.random(),
        onClick = {}
    )
}

@Preview
@Composable
private fun KwikCountryCodeButtonWithFlagsPreview(){
    KwikCountryCodeButton(
        country = countryList.random(),
        showFlags = true,
        showDialingCode = true,
        showCountryCode = true,
        onClick = {}
    )
}