package com.isakaro.kwik

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.destinations.KwikAccordionScreenDestination
import com.isakaro.kwik.destinations.KwikAvatarScreenDestination
import com.isakaro.kwik.destinations.KwikBiometricsScreenDestination
import com.isakaro.kwik.destinations.KwikBottomTabsScreenDestination
import com.isakaro.kwik.destinations.KwikBottomTabsScreenStyledDestination
import com.isakaro.kwik.destinations.KwikButtonScreenDestination
import com.isakaro.kwik.destinations.KwikCardScreenDestination
import com.isakaro.kwik.destinations.KwikCarouselScreenDestination
import com.isakaro.kwik.destinations.KwikCheckBoxScreenDestination
import com.isakaro.kwik.destinations.KwikColorsScreenDestination
import com.isakaro.kwik.destinations.KwikCounterScreenDestination
import com.isakaro.kwik.destinations.KwikCountryPickerScreenDestination
import com.isakaro.kwik.destinations.KwikDateScreenDestination
import com.isakaro.kwik.destinations.KwikDialogScreenDestination
import com.isakaro.kwik.destinations.KwikDropDownScreenDestination
import com.isakaro.kwik.destinations.KwikFilterChipScreenDestination
import com.isakaro.kwik.destinations.KwikGridScreenDestination
import com.isakaro.kwik.destinations.KwikOutlinedTextFieldScreenDestination
import com.isakaro.kwik.destinations.KwikPermissionsScreenDestination
import com.isakaro.kwik.destinations.KwikProgressIndicatorScreenDestination
import com.isakaro.kwik.destinations.KwikRadioButtonScreenDestination
import com.isakaro.kwik.destinations.KwikRatingBarScreenDestination
import com.isakaro.kwik.destinations.KwikSearchViewScreenDestination
import com.isakaro.kwik.destinations.KwikShapeScreenDestination
import com.isakaro.kwik.destinations.KwikSliderScreenDestination
import com.isakaro.kwik.destinations.KwikStepperScreenDestination
import com.isakaro.kwik.destinations.KwikSwitchScreenDestination
import com.isakaro.kwik.destinations.KwikTabScreenDestination
import com.isakaro.kwik.destinations.KwikTagsInputScreenDestination
import com.isakaro.kwik.destinations.KwikTextFieldScreenDestination
import com.isakaro.kwik.destinations.KwikTimelineScreenDestination
import com.isakaro.kwik.destinations.KwikToastScreenDestination
import com.isakaro.kwik.destinations.KwikToggleButtonScreenDestination
import com.isakaro.kwik.destinations.KwikTypographyScreenDestination
import com.isakaro.kwik.destinations.KwikWebViewScreenDestination
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.inputfields.KwikSearchView
import com.isakaro.kwik.ui.list.KwikLazyList
import com.isakaro.kwik.ui.list.KwikListItemAction
import com.isakaro.kwik.ui.list.KwikListItemActionState
import com.isakaro.kwik.ui.spacer.KwikHSpacer
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.ui.text.KwikText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
internal fun KwikComponentsCatalogScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val components = listOf(
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
                title = "Bottom Tabs",
                description = "Bottom navigation bar for modern apps",
                action = {
                    navigator.navigate(KwikBottomTabsScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Bottom Tabs (Styled)",
                description = "Showcasing the styled bottom tabs. Went for floating view style",
                action = {
                    navigator.navigate(KwikBottomTabsScreenStyledDestination)
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
                title = "Sliders",
                description = "Slider components with custom thumb and track. Includes range and normal slider",
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
                title = "Date pickers",
                description = "Date picker components. Includes date, date range, time and date-time pickers, as well as a date input",
                action = {
                    navigator.navigate(KwikDateScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Rating Bar",
                description = "Rating bar component",
                action = {
                    navigator.navigate(KwikRatingBarScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Country picker",
                description = "Component for selecting a country",
                action = {
                    navigator.navigate(KwikCountryPickerScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Search view",
                description = "Provides a neat search view with suggestions and debounce support",
                action = {
                    navigator.navigate(KwikSearchViewScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Stepper",
                description = "Provides a stepper component for keeping track of steps",
                action = {
                    navigator.navigate(KwikStepperScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Toggle button",
                description = "Toggle button component",
                action = {
                    navigator.navigate(KwikToggleButtonScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Counter",
                description = "Counter component",
                action = {
                    navigator.navigate(KwikCounterScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Filter Chips",
                description = "Filter chips component",
                action = {
                    navigator.navigate(KwikFilterChipScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Carousel (Slider)",
                description = "Carousel component for easily displaying content on scrollable, multiple pages",
                action = {
                    navigator.navigate(KwikCarouselScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Avatar",
                description = "Avatar component that can load images from urls, resources or vectors",
                action = {
                    navigator.navigate(KwikAvatarScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Shapes",
                description = "Showcases different shapes",
                action = {
                    navigator.navigate(KwikShapeScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Colors",
                description = "Shows all color schemes in the theme",
                action = {
                    navigator.navigate(KwikColorsScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Typography (Text)",
                description = "Showcases all typography styles in the theme",
                action = {
                    navigator.navigate(KwikTypographyScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Web view",
                description = "Web view component for displaying web pages. Supports Javascript native bridge, file upload, multi-windows and more...",
                action = {
                    navigator.navigate(KwikWebViewScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Timeline",
                description = "Timeline component for showing events in a vertical fashion",
                action = {
                    navigator.navigate(KwikTimelineScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Biometrics",
                description = "Component for authenticating users with biometrics. Supports fingerprint, face, iris, PIN...",
                action = {
                    navigator.navigate(KwikBiometricsScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Tags input",
                description = "Allows the user to input tags. Whether preset or custom",
                action = {
                    navigator.navigate(KwikTagsInputScreenDestination)
                }
            )
        ),
        KwikListItemActionState.Data(
            KwikListItemAction(
                title = "Grid",
                description = "Very flexible grid system that can be used to display any content",
                action = {
                    navigator.navigate(KwikGridScreenDestination)
                }
            )
        )
    ).sortedBy { it.action.title }

    val listState = rememberLazyListState()
    val searchQuery = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val searchResults = remember { mutableStateOf(components) }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                KwikImageView(
                    modifier = Modifier.size(40.dp),
                    url = R.drawable.kwikui_logo
                )

                KwikHSpacer(8)

                KwikText.HeadlineMedium(
                    text = "Kwik Components Catalog"
                )
            }

            KwikVSpacer(24)

            KwikSearchView(
                modifier = Modifier.height(60.dp),
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
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikComponentsCatalogScreen() {
    KwikComponentsCatalogScreen()
}
