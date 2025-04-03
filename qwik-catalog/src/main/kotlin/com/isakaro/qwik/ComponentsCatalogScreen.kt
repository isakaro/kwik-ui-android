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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
internal fun ComponentsCatalogScreen(
    navigator: DestinationsNavigator = navigator()
) {

    val components = listOf(
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "AppBar",
                description = "Top app bar",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Permissions",
                description = "Easy-to-use Permission handler",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Buttons",
                description = "Versatile button component",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Tabs",
                description = "Modern tab layout with support for counts and more",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "BottomSheet",
                description = "Bottom sheet with support for multiple states",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Bottom Navigation",
                description = "Bottom navigation bar for modern apps",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Card",
                description = "Card component",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "CheckBox",
                description = "Checkbox component which includes tri-state ability",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Dialog",
                description = "Dialog component for handling various scenarios",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "DropDown",
                description = "Dropdown component",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Progress Indicator",
                description = "Linear and circular progress indicators",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Radio Button",
                description = "Radio button component",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Slider",
                description = "Slider component",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Toast",
                description = "Modern toast component",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Switch",
                description = "Switch component",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "TextField (Outlined)",
                description = "Powerful OutlinedTextField component that handles most use cases",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "TextField (Filled)",
                description = "Powerful TextField component that handles most use cases",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Accordion",
                description = "Accordion component for expandable content",
                action = {  }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Date range picker",
                description = "Date range picker component using Material3 DatePicker underneath",
                action = {  }
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
    ComponentsCatalogScreen()
}
