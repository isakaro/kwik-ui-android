package app.isakaro.ui.library

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.isakaro.ui.library.theme.ColorSecondaryAccent
import app.isakaro.ui.library.utils.text
import com.isakaro.qwik.ui.library.R
import com.isakaro.ui.components.IsakaroDialog
import com.isakaro.ui.components.IsakaroSearchView

@Composable
fun CountrySelector(
    state: LazyListState,
    onSelect: (CountryInfo) -> Unit
){
    val searchResults = remember { mutableStateOf(countryList) }
    val countries = remember { countryList }
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        IsakaroSearchView(
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

        //if search box is not empty but no results are found, show a message
        if (searchResults.value.isEmpty() && searchQuery.text.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.no_such_country_found),
                modifier = Modifier.padding(bottom = 100.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }

        LazyColumn(state = state, modifier = Modifier.fillMaxSize().weight(1f)) {
            itemsIndexed(searchResults.value) { index, country ->
                CountryCodeItem(country = country){
                    onSelect(country)
                }
            }
        }

    }
}

@Composable
fun CountrySelectorDialog(
    open: Boolean,
    countryListState: LazyListState,
    onSelect: (CountryInfo) -> Unit,
    onDismiss: () -> Unit
){
    IsakaroDialog.ContentDialog(
        open = open,
        title = stringResource(id = R.string.where_are_you_from),
        withCloseIcon = true,
        dismiss = { onDismiss() }
    ) {
        CountrySelector(
            state = countryListState,
            onSelect = { onSelect(it) },
        )
    }
}

@Composable
fun CountryCodeButton(
    country: CountryInfo,
    modifier: Modifier = Modifier.height(65.dp),
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
fun CountryCodeItem(country: CountryInfo, onClick: () -> Unit) {
    Box(modifier = Modifier
        .height(80.dp)
        .fillMaxWidth()
        .padding(10.dp)
        .clickable {
            onClick()
        }) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.Center)) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 10.dp),
                painter = painterResource(id = country.flag),
                contentDescription = country.name
            )
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
fun CountrySelectorPreview(){
    CountrySelector(
        state = rememberLazyListState(),
        onSelect = {},
    )
}