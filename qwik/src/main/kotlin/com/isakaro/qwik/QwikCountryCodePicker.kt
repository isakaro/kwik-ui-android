package com.isakaro.qwik

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
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
import com.isakaro.qwik.theme.ColorSecondaryAccent
import com.isakaro.qwik.utils.CountryInfo
import com.isakaro.qwik.utils.countryList
import com.isakaro.qwik.utils.resolveCountries
import com.isakaro.qwik.utils.text

@Composable
fun QwikCountryCodePicker(
    state: LazyListState,
    omitCountries: List<String> = emptyList(),
    showFlags: Boolean = true,
    noCountryFoundMessage: String = "No country found",
    onSelect: (CountryInfo) -> Unit
){
    val countries = remember { resolveCountries(omitCountries) }
    val searchResults = remember { mutableStateOf(countries) }
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        QwikSearchView(
            state = searchQuery,
            onTextChange = { query ->
                if (query.isBlank()) {
                    searchResults.value = countryList
                }
                searchResults.value = countries.filter { country ->
                    val searchTerm = query.trim().lowercase()
                    val tags = country.tags.joinToString(" ") { it.lowercase() }
                    country.name.lowercase().contains(searchTerm) || country.dialingCode.contains(searchTerm) || tags.contains(searchTerm)
                }
            },
            onTextCleared = {
                searchResults.value = countryList
            }
        )

        if (searchResults.value.isEmpty() && searchQuery.text.isNotEmpty()) {
            Text(
                text = noCountryFoundMessage,
                modifier = Modifier.padding(bottom = 100.dp),
                style = MaterialTheme.typography.titleMedium
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
fun QwikCountryCodePickerDialog(
    open: Boolean,
    title: String = "Where are you from?",
    countryListState: LazyListState,
    onSelect: (CountryInfo) -> Unit,
    onDismiss: () -> Unit
){
    QwikDialog.ContentDialog(
        open = open,
        title = title,
        withCloseIcon = true,
        dismiss = { onDismiss() }
    ) {
        QwikCountryCodePicker(
            state = countryListState,
            onSelect = { onSelect(it) },
        )
    }
}

@Composable
fun QwikCountryCodeButton(
    modifier: Modifier = Modifier.height(65.dp),
    country: CountryInfo,
    enabled: Boolean = true,
    onClick: () -> Unit
){
    Button(
        onClick = { onClick() },
        modifier = modifier.alpha(if (enabled) 1.5f else 0.5f),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(8.dp),
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = country.code,
                color = ColorSecondaryAccent,
                modifier = Modifier,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = country.dialingCode,
                color = ColorSecondaryAccent,
                style = MaterialTheme.typography.headlineSmall
            )
            Icon(
                Icons.Filled.KeyboardArrowDown,
                tint = ColorSecondaryAccent,
                modifier = Modifier.size(35.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
fun CountryCodeItem(
    country: CountryInfo,
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
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = country.name,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = country.dialingCode,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}

@Preview
@Composable
fun QwikCountryCodePickerPreview(){
    QwikCountryCodePicker(
        state = rememberLazyListState(),
        onSelect = {},
    )
}