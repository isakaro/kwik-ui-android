package com.isakaro.qwik

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
import com.isakaro.qwik.catalog.navigator.NavigationRoute
import com.isakaro.qwik.catalog.navigator.NavigationRoute.AppBarScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.BottomNavScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.BottomSheetScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.ButtonScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.CardScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.CheckBoxScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.DialogScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.DropDownScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.ProgressIndicatorScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.RadioButtonScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.SliderScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.SwitchScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.TabScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.TextFieldScreen

@Composable
internal fun StartScreen(
    navClick: (NavigationRoute) -> Unit = {}
) {

    val components = listOf(
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "AppBar",
                description = "Top app bar",
                action = { navClick(AppBarScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Permissions",
                description = "Easy-to-use Permission handler",
                action = { navClick(NavigationRoute.PermissionScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Buttons",
                description = "Versatile button component",
                action = { navClick(ButtonScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Tabs",
                description = "Modern tab layout with support for counts and more",
                action = { navClick(TabScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "BottomSheet",
                description = "Bottom sheet with support for multiple states",
                action = { navClick(BottomSheetScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Bottom Navigation",
                description = "Bottom navigation bar for modern apps",
                action = { navClick(BottomNavScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Card",
                description = "Card component",
                action = { navClick(CardScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "CheckBox",
                description = "Checkbox component which includes tri-state ability",
                action = { navClick(CheckBoxScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Dialog",
                description = "Dialog component for handling various scenarios",
                action = { navClick(DialogScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "DropDown",
                description = "Dropdown component",
                action = { navClick(DropDownScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Progress Indicator",
                description = "Linear and circular progress indicators",
                action = { navClick(ProgressIndicatorScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Radio Button",
                description = "Radio button component",
                action = { navClick(RadioButtonScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Slider",
                description = "Slider component",
                action = { navClick(SliderScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Toast",
                description = "Modern toast component",
                action = { navClick(NavigationRoute.ToastScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Switch",
                description = "Switch component",
                action = { navClick(SwitchScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "TextField (Outlined)",
                description = "Powerful OutlinedTextField component that handles most use cases",
                action = { navClick(NavigationRoute.OutlinedTextFieldScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "TextField (Filled)",
                description = "Powerful TextField component that handles most use cases",
                action = { navClick(TextFieldScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Accordion",
                description = "Accordion component for expandable content",
                action = { navClick(NavigationRoute.AccordionScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Date range picker",
                description = "Date range picker component using Material3 DatePicker underneath",
                action = { navClick(NavigationRoute.DateRangePicker) }
            )
        )
    ).sortedBy { it.action.title }

    val listState = rememberLazyListState()
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    val searchResults = remember { mutableStateOf(components) }

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        QwikText.TitleText(
            text = "Qwik Components Catalog",
            style = MaterialTheme.typography.headlineSmall
        )

        QwikVSpacer(24)

        QwikSearchView(
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

        QwikVSpacer(24)

        QwikLazyList(
            state = listState,
            items = searchResults.value
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    StartScreen()
}
