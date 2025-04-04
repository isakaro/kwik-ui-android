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
import com.isakaro.kwik.destinations.KwikBottomTabsScreenDestination
import com.isakaro.kwik.destinations.KwikButtonScreenDestination
import com.isakaro.kwik.destinations.KwikCardScreenDestination
import com.isakaro.kwik.destinations.KwikCheckBoxScreenDestination
import com.isakaro.kwik.destinations.KwikDateRangePickerScreenDestination
import com.isakaro.kwik.destinations.KwikDialogScreenDestination
import com.isakaro.kwik.destinations.KwikDropDownScreenDestination
import com.isakaro.kwik.destinations.KwikOutlinedTextFieldScreenDestination
import com.isakaro.kwik.destinations.KwikPermissionsScreenDestination
import com.isakaro.kwik.destinations.KwikProgressIndicatorScreenDestination
import com.isakaro.kwik.destinations.KwikRadioButtonScreenDestination
import com.isakaro.kwik.destinations.KwikSliderScreenDestination
import com.isakaro.kwik.destinations.KwikSwitchScreenDestination
import com.isakaro.kwik.destinations.KwikTabScreenDestination
import com.isakaro.kwik.destinations.KwikTextFieldScreenDestination
import com.isakaro.kwik.destinations.KwikToastScreenDestination
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
                    navigator.navigate(KwikPermissionsScreenDestination)
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
                action = {
                    navigator.navigate(KwikBottomTabsScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Bottom Tabs Navigation",
                description = "Bottom navigation bar for modern apps",
                action = {
                    navigator.navigate(KwikBottomTabsScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Card",
                description = "Card component",
                action = {
                    navigator.navigate(KwikCardScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "CheckBox",
                description = "Checkbox component which includes tri-state ability",
                action = {
                    navigator.navigate(KwikCheckBoxScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Dialog",
                description = "Dialog component for handling various scenarios",
                action = {
                    navigator.navigate(KwikDialogScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "DropDown",
                description = "Dropdown component",
                action = {
                    navigator.navigate(KwikDropDownScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Progress Indicator",
                description = "Linear and circular progress indicators",
                action = {
                    navigator.navigate(KwikProgressIndicatorScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Radio Button",
                description = "Radio button component",
                action = {
                    navigator.navigate(KwikRadioButtonScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Slider",
                description = "Slider component",
                action = {
                    navigator.navigate(KwikSliderScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Toast",
                description = "Modern toast component",
                action = {
                    navigator.navigate(KwikToastScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Switch",
                description = "Switch component",
                action = {
                    navigator.navigate(KwikSwitchScreenDestination)
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
