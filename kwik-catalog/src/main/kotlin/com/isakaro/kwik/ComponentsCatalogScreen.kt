package com.isakaro.kwik

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.destinations.KwikAccordionScreenDestination
import com.isakaro.kwik.destinations.KwikAppBarScreenDestination
import com.isakaro.kwik.destinations.KwikButtonScreenDestination
import com.isakaro.kwik.destinations.KwikDateRangePickerScreenDestination
import com.isakaro.kwik.destinations.KwikOutlinedTextFieldScreenDestination
import com.isakaro.kwik.destinations.KwikTabScreenDestination
import com.isakaro.kwik.destinations.KwikTextFieldScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
internal fun ComponentsCatalogScreen(
    navigator: DestinationsNavigator = navigator()
) {

    val components = listOf(
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "AppBar",
                description = "Top app bar",
                action = {
                    navigator.navigate(KwikAppBarScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Permissions",
                description = "Easy-to-use Permission handler",
                action = {

                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Buttons",
                description = "Versatile button component",
                action = {
                    navigator.navigate(KwikButtonScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Tabs (Horizontal)",
                description = "Modern tab layout with support for counts and more",
                action = {
                    navigator.navigate(KwikTabScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "BottomSheet",
                description = "Bottom sheet with support for multiple states",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Bottom Navigation",
                description = "Bottom navigation bar for modern apps",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Card",
                description = "Card component",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "CheckBox",
                description = "Checkbox component which includes tri-state ability",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Dialog",
                description = "Dialog component for handling various scenarios",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "DropDown",
                description = "Dropdown component",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Progress Indicator",
                description = "Linear and circular progress indicators",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Radio Button",
                description = "Radio button component",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Slider",
                description = "Slider component",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Toast",
                description = "Modern toast component",
                action = {  }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Switch",
                description = "Switch component",
                action = {
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "TextField (Outlined)",
                description = "Powerful OutlinedTextField component that handles most use cases",
                action = {
                    navigator.navigate(KwikOutlinedTextFieldScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "TextField (Filled)",
                description = "Powerful TextField component that handles most use cases",
                action = {
                    navigator.navigate(KwikTextFieldScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Accordion",
                description = "Accordion component for expandable content",
                action = {
                    navigator.navigate(KwikAccordionScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Date range picker",
                description = "Date range picker component using Material3 DatePicker underneath",
                action = {
                    navigator.navigate(KwikDateRangePickerScreenDestination)
                }
            )
        )
    ).sortedBy { it.action.title }

    val listState = rememberLazyListState()
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    val searchResults = remember { mutableStateOf(components) }

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        KwikText.TitleText(
            text = "Kwik Components Catalog",
            style = MaterialTheme.typography.headlineSmall
        )

        KwikVSpacer(24)

        KwikSearchView(
            state = searchQuery,
            placeholder = "Search components...",
            onTextChange = { query ->
                if (query.isBlank()) {
                    searchResults.value = components
                }
                searchResults.value = components.filter { component ->
                    val searchTerm = query.trim().lowercase()
                    component.action.title.lowercase().contains(searchTerm) || component.action.description.contains(searchTerm)
                }
            },
            onTextCleared = {
                searchResults.value = components
            }
        )

        KwikVSpacer(24)

        KwikLazyList(
            state = listState,
            items = searchResults.value
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStartScreen() {
    ComponentsCatalogScreen()
}
