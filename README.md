# KwikUI

A comprehensive Android UI library designed to accelerate your development process by providing pre-built, customizable UI components. Spend less time on boilerplate and more time focusing on your app's core functionality.

![KwikUI Logo](media/KwikUI.png)

## All Components

KwikUI includes a comprehensive collection of components:

- **Accordion** (accordion, accordion group)
- **Biometrics**
- **Bottom Tabs**
- **Button**
- **Card**
- **Carousel** (slider)
- **Checkbox**
- **Counter**
- **Country picker**
- **Date Picker** (date input, date picker, date range picker)
- **Dialogs** (modals)
- **Dropdown**
- **Filter Chips**
- **Grid** (CSS-like)
- **Permission Handler**
- **Progress Bar**
- **Radio** (group)
- **Rating Bar**
- **Search View** with autocomplete and debounce capabilities
- **Sliders** (range sliders)
- **Stepper**
- **Switch**
- **Tags Input**
- **Text Fields** (filled, outlined with suggestions feature and debounce)
- **Timeline**
- **Toast**
- **Toggle Button**
- **Webview**

### Accordion
Accordion component that can be expanded or collapsed.

<table>
  <tr>
    <td><img src="media/accordion/light1.jpg" alt="Accordion Light Mode" width="400"/></td>
    <td><img src="media/accordion/dark1.jpg" alt="Accordion Dark Mode" width="400"/></td>
    <td><img src="media/accordion/dark2.jpg" alt="Accordion Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
val items = listOf(
    KwikAccordionItem("Tortuga", "A lawless island for pirates to hide and do business"),
    KwikAccordionItem("Isla de Muerta", "Can only be found by those who already know where it is"),
    KwikAccordionItem("Davy Jones' Locker", "You don't want to end up there, trust me"),
)

KwikAccordionGroup(
    items = items,
    titleProvider = { it.title },
    elevation = 2,
    errorProvider = { it.hasError },
    content = { item ->
        KwikText.BodyMedium(text = item.content, modifier = Modifier.padding(16.dp))
    }
)
```

[Accordion docs](https://isakaro.com)

---

### Bottom Tabs
Bottom tabs component that can display multiple tabs.

<table>
  <tr>
    <td><img src="media/bottomtabs/light1.jpg" alt="Bottom Tabs Light Mode" width="400"/></td>
    <td><img src="media/bottomtabs/dark1.jpg" alt="Bottom Tabs Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
val navItems = listOf(
    KwikTabItem(
        title = "Home",
        icon = Icons.Rounded.Home,
        content = {
            Content("Home")
        }
    ),
    KwikTabItem(
        title = "Discover",
        icon = Icons.Rounded.LocationOn,
        content = {
            Content("Discover")
        }
    )
)

val pagerState = rememberPagerState(
    initialPage = 0,
    initialPageOffsetFraction = 0f
) {
    navItems.size
}

KwikBottomTabs(
    modifier = Modifier.height(100.dp).padding(4.dp),
    shape = MaterialTheme.shapes.large,
    elevation = 8,
    tabs = navItems,
    pagerState = pagerState
)
```

[Bottom Tabs docs](https://isakaro.com)

---

### Buttons
Easily create various button styles with customizable colors, shapes, and sizes. Supports different states like enabled, disabled, and loading.

<table>
  <tr>
    <td><img src="media/button/light1.jpg" alt="Button Light Mode" width="400"/></td>
    <td><img src="media/button/dark1.jpg" alt="Button Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
KwikButton(
    text = "Submit",
    onClick = {
        // Handle click event
    }
)

// Loading state button
KwikButton(
    text = "Loading",
    isLoading = true
)

// Button with trailing icon
KwikButton(
    text = "Action",
    trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
    onClick = {  }
)
```

[Button docs](https://isakaro.com)

---

### Cards
Cards with customizable colors, shapes, and sizes. Includes ready-to-use components such as image cards...

```kotlin
KwikCard(){
    // content
}
```

[Card docs](https://isakaro.com)

---

### Carousel (Slider)
Carousel component for displaying images or other content in a sliding format. Supports looping and autoplay features.

```kotlin
val carouselState = rememberKwikCarouselState(
    KwikCarouselState(
        itemCount = images.size,
        loop = true
    )
)

KwikImageCarousel(
    modifier = Modifier.height(200.dp),
    state = carouselState,
    autoPlay = true,
    images = images
)
```

[Carousel docs](https://isakaro.com)

---

### Checkbox
Checkbox component for selecting options. Supports different states like checked, unchecked, and indeterminate.

```kotlin
 var checked by remember { mutableStateOf(true) }

KwikCheckBox(
    checked = checked,
    onCheckedChange = {
        checked = it
    }
)
```

[Checkbox docs](https://isakaro.com)

### Counter
Counter component for incrementing or decrementing values. Supports different styles and sizes.

```kotlin
var counterState by remember { mutableStateOf(0) }

KwikCounter(
    label = "Counter",
    initialValue = 2,
    onValueChange = {
        counterState = it
    }
)
```

[Counter docs](https://isakaro.com)

---

### Country Picker
Country picker component for selecting countries. Supports search functionality and customizable appearance.

```kotlin
KwikCountryCodePicker(
    state = rememberLazyListState(),
    onSelect = {
        // handle selected country
    },
)
```
[Country Picker docs](https://isakaro.com)

---

### Date Picker
Date picker component for selecting dates. Supports different styles and formats.

<table>
  <tr>
    <td><img src="media/date/light1.jpg" alt="Bottom Tabs Light Mode" width="400"/></td>
    <td><img src="media/date/dark1.jpg" alt="Bottom Tabs Dark Mode" width="400"/></td>
  </tr>
  <tr>
    <td><img src="media/date/light2.jpg" alt="Bottom Tabs Light Mode" width="400"/></td>
    <td><img src="media/date/dark2.jpg" alt="Bottom Tabs Dark Mode" width="400"/></td>
  </tr>
  <tr>
    <td><img src="media/date/light3.jpg" alt="Bottom Tabs Light Mode" width="400"/></td>
    <td><img src="media/date/dark3.jpg" alt="Bottom Tabs Dark Mode" width="400"/></td>
  </tr>
</table>

## Date Picker (Input)
```kotlin
var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

KwikDateFieldButton(
    label = "Date of birth",
    placeholder = "Enter your date of birth",
    mode = KwikDatePickerMode.Input,
    selected = {
        selectedDate = it
    }
)
```

## Date Picker (Picker)
```kotlin
var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

KwikDateFieldButton(
    label = "Date of birth",
    placeholder = "Enter your date of birth",
    mode = KwikDatePickerMode.Picker,
    selected = {
        selectedDate = it
    }
)
```

## Date Range Picker
```kotlin
var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

KwikDateFieldButton(
    label = "Date of birth",
    placeholder = "Enter your date of birth",
    mode = KwikDatePickerMode.Input,
    selected = {
        selectedDate = it
    }
)
```

[Date Picker docs](https://isakaro.com)

---

### Dialogs
Dialogs for displaying messages or prompts. Supports different styles and customizable content.

<table>
  <tr>
    <td><img src="media/dialog/light1.jpg" alt="Dialog Light Mode" width="400"/></td>
    <td><img src="media/dialog/dark1.jpg" alt="Dialog Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
val openDialog = remember { mutableStateOf(false) }

KwikDialog.ConfirmDialog(
    open = openConfirmDialog,
    onConfirm = {
        openConfirmDialog = false
    },
    dismiss = {
        openConfirmDialog = false
    }
){
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        KwikText.TitleSmall(
            text = "The only rules that really matter are these: what a man can do and what a man can’t do.",
        )
    }
}
```

[Dialog docs](https://isakaro.com)

---

### Dropdown
Dropdown component for selecting options from a list. Supports search functionality and customizable appearance.

<table>
  <tr>
    <td><img src="media/dropdown/light1.jpg" alt="Dialog Light Mode" width="400"/></td>
    <td><img src="media/dropdown/dark1.jpg" alt="Dialog Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
var selectedOption by remember { mutableStateOf("") }

val items = listOf(
    KwikDropdownItemActionState.Data(
        KwikDropdownItem(
            text = {
                KwikText.BodyMedium(text = "Profile")
            },
            leadingIcon = {
                KwikImageView(
                    url = Icons.Default.AccountCircle
                )
            },
            onClick = {
                // Handle click event
            }
        )
    )
)

KwikDropdown(
    state = state,
    onDismissRequest = { state = false },
    items = items
)
```

[Dropdown docs](https://isakaro.com)

---

### Filter Chips
Filter chips for filtering content based on selected options. Supports different styles and customizable appearance.

<table>
  <tr>
    <td><img src="media/filterchip/light1.jpg" alt="Filter Chips Light Mode" width="400"/></td>
    <td><img src="media/filterchip/dark1.jpg" alt="Filter Chips Dark Mode" width="400"/></td>
  </tr>
  <tr>
    <td><img src="media/filterchip/light2.jpg" alt="Filter Chips Light Mode" width="400"/></td>
  </tr>
</table>

```kotlin
val filters = listOf(
    KwikFilterChipOption("Option 1", "1"),
    KwikFilterChipOption("Option 2", "2"),
    KwikFilterChipOption("Option 3", "3"),
    KwikFilterChipOption("Option 4", "4"),
    KwikFilterChipOption("Option 5", "5")
)

var selected by remember { mutableStateOf(filters) }

KwikTheme {
    KwikFilterChips(
        filters = filters,
        flowLayout = true,
        preSelection = setOf(filters.random()),
        filtersUpdated = { selected = it }
    )
}
```

[Filter Chips docs](https://isakaro.com)

---

### Grid
Grid component for displaying items in a grid layout. Supports different styles and customizable appearance.

<table>
  <tr>
    <td><img src="media/grid/light1.jpg" alt="Grid Light Mode" width="400"/></td>
    <td><img src="media/grid/dark1.jpg" alt="Grid Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
val items = listOf(
    KwikDiv(
        colSpan = 3,
        colPosition = 0,
        rowPosition = 0,
        onClick = {},
        content = {
            KwikImageView(
                modifier = Modifier.background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                url = KwikConstants.SAMPLE_IMAGE
            )
        }
    )
)

KwikGrid(
    modifier = Modifier.fillMaxSize().height(300.dp),
    cols = 4,
    rows = 3,
    gap = 4,
    items = items
)
```

[Grid docs](https://isakaro.com)

---

### Permission Handlers
Permission handlers for managing app permissions. Supports different styles and customizable appearance.

```kotlin
val permissionState = rememberKwikPermissionState()

KwikPermissionsRequest(
            state = permissionState.value,
            permissions = listOf(
                KwikPermissionDto(Manifest.permission.READ_MEDIA_IMAGES, "Allow app to access your photos and videos to use while creating a listing."),
                KwikPermissionDto(Manifest.permission.READ_MEDIA_VIDEO, "Allow app to access your photos and videos to use while creating a listing.")
            ),
            title = "Media access required",
            image = { // optional image to show
                KwikImageView(
                    modifier = Modifier.size(120.dp),
                    url = Icons.Default.Build,
                    tint = Color.Gray
                )
            },
            onGrantAction = { 
                // action to perform when permissions are granted
                granted = true
            },
            onDeniedAction = {
                // action to perform when permissions are denied
            },
            onCancel = {
                // action to perform when user cancels the permission request
            }
        )
```

[Permission Handlers docs](https://isakaro.com)

---

### Progress
Progress component for displaying loading indicators. Supports different styles and customizable appearance.

## Loading View with text
```kotlin
KwikLoadingView(
    text = "Loading... Please Wait..."
)
```

## Circular Loading
```kotlin
KwikCircularLoading()
```

## Linear Loading and custom track color
```kotlin
KwikLinearLoading(
    trackColor = Color.White
)
```

[Progress docs](https://isakaro.com)

---

### Radio Groups
Radio groups for selecting options. Supports different styles and customizable appearance.

<img src="media/radio/light1.jpg" alt="Radio Group Light Mode" width="400"/>

```kotlin
val options = listOf(
    KwikRadioItem("Captain Jack Sparrow", 1),
    KwikRadioItem("Captain Hector Barbossa", 2),
    KwikRadioItem("Calypso", 3),
    KwikRadioItem("Davy Jones", 4),
)

val (selectedOption, onOptionSelected) = remember { mutableStateOf<KwikRadioItem<Int>?>(null) }

KwikRadioButtonGroup(
    options = options,
    onOptionSelected = {
        onOptionSelected(it)
    }
)
```

[Radio Group docs](https://isakaro.com)

---

### Rating Bars
Rating bars for displaying and selecting ratings. Supports different styles and customizable appearance.

<table>
  <tr>
    <td><img src="media/rating/light1.jpg" alt="Rating Bar Light Mode" width="400"/></td>
    <td><img src="media/rating/dark1.jpg" alt="Rating Bar Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
var userRating by remember { mutableIntStateOf(0) }

KwikRatingBar(
    stars = 5,
    rating = userRating.toDouble(),
    clickable = true,
    showBadge = false,
    starSize = 60.dp,
    onClick = { rating ->
        userRating = rating
    }
)
```

[Rating Bar docs](https://isakaro.com)

---

### Search View
Search view component for searching content. Supports different styles, customizable appearance and suggestions.

<table>
  <tr>
    <td><img src="media/searchview/light1.jpg" alt="Search View Light Mode" width="400"/></td>
    <td><img src="media/searchview/dark1.jpg" alt="Search View Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
val query = rememberSaveable(stateSaver = TextFieldValue.Saver) {
    mutableStateOf(
        TextFieldValue("")
    )
}

KwikSearchView(
    state = query,
    placeholder = "Enter address...",
    onTextChange = { query ->

    },
    suggestions = listOf("Tortuga", "Isla de Muerta", "Shipwreck Cove", "Davy Jones' Locker")
)
```

[Search View docs](https://isakaro.com)

---

### Sliders
Sliders for selecting values within a range. Supports different styles and customizable appearance.

<table>
  <tr>
    <td><img src="media/slider/light1.jpg" alt="Slider Light Mode" width="400"/></td>
    <td><img src="media/slider/dark1.jpg" alt="Slider Dark Mode" width="400"/></td>
  </tr>
</table>

## Range slider
```kotlin
var sliderPosition by remember { mutableStateOf(20f..80f) }

KwikRangeSlider(
    value = sliderPosition,
    onValueChange = { range ->
        sliderPosition = range
    }
)
```

## Single slider
```kotlin
var sliderPosition by remember { mutableFloatStateOf(0f) }

KwikSlider(
    value = sliderPosition,
    onValueChange = { range ->
        sliderPosition = range
    }
)
```

[Slider docs](https://isakaro.com)

---

### Stepper
Stepper component for incrementing or decrementing values. Supports different styles and customizable appearance.

<table>
  <tr>
    <td><img src="media/stepper/light1.jpg" alt="Stepper Light Mode" width="400"/></td>
    <td><img src="media/stepper/dark1.jpg" alt="Stepper Dark Mode" width="400"/></td>
  </tr>
</table>

```kotlin
val kwikStepperState = rememberKwikStepperState(
    steps = listOf("Request", "Verify", "Dispatch", "Delivered")
)

KwikStepper(
    state = kwikStepperState
)
```

## Controlling the Stepper
```kotlin
kwikStepperState.moveForward() // Move to the next step

kwikStepperState.moveBackward() // Move to the next step

kwikStepperState.completeAll() // Move to the next step

kwikStepperState.clearAll() // Move to the next step

kwikStepperState.moveToStep() // Move to a specific step
```

[Stepper docs](https://isakaro.com)

---

### Switch
Switch component for toggling between two states. Supports different styles and customizable appearance.

```kotlin
val checkedState = remember { mutableStateOf(true) }

KwikSwitch(
    text = { KwikText.BodyMedium(text = "Control the lights") },
    checked = checkedState.value,
    onCheckedChange = { checkedState.value = it }
)
```

[Switch docs](https://isakaro.com)

---

### Tabs
Tabs component for displaying multiple tabs. Supports different styles and customizable appearance.

<table>
  <tr>
    <td><img src="media/tabs/light1.jpg" alt="Tabs Light Mode" width="400"/></td>
    <td><img src="media/tabs/dark1.jpg" alt="Tabs Dark Mode" width="400"/></td>
  </tr>
</table>

### Text Fields
KwikUI offers a range of text field styles to suit your needs:
- **Filled Text Fields:** Standard text fields with a filled background.
- **Outlined Text Fields:** Text fields with an outlined border.
- **OTP Fields:** Specialized text fields for collecting One-Time Passcodes (OTP). Provides features like automatic focus and input masking.

![Text Field Light Mode](media/textfield-light.png) ![Text Field Dark Mode](media/textfield-night.png)

```kotlin
var email by remember { mutableStateOf("") }

KwikTextField(
    value = email,
    onValueChange = { email = it },
    style = KwikTextFieldStyle.Outlined,
    hint = "Email Address",
    keyboardType = KeyboardType.Email
)

// OTP Field
var otpValue by remember { mutableStateOf("") }

KwikOtpField(
    value = otpValue,
    onValueChange = { otpValue = it },
    length = 6,
    autoFocus = true
)
```

[Button docs](https://isakaro.com)

---

### Webview
Integrate web content seamlessly with the pre-configured webview. Includes features like file upload handling, native bridge communication, Javascript support, multi-window support, progress indicators, and more.

![Webview](media/webview/vid1.gif)

```kotlin
KwikWebview(
    url = "https://example.com"
)
```

---

### Permission Handlers
Simplify permission management with easy-to-use handlers. Request permissions with clear callbacks for granted and denied scenarios.

![Permission Handler Light Mode](media/permission-light.png) ![Permission Handler Dark Mode](media/permission-night.png)

```kotlin
KwikPermissionHandler(
    permissions = listOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
    onGranted = {
        // Handle granted permissions
    },
    onDenied = {
        // Handle denied permissions
    }
)
```

---

### Radio Groups
Create and manage radio button groups effortlessly. Customize the appearance and handle selection changes with minimal code.

![Radio Group Light Mode](media/radiogroup-light.png) ![Radio Group Dark Mode](media/radiogroup-night.png)

```kotlin
var selectedOption by remember { mutableStateOf("option1") }

KwikRadioGroup(
    selectedOption = selectedOption,
    onOptionSelected = { selectedOption = it },
    orientation = Orientation.Vertical
) {
    KwikRadioOption(
        value = "option1",
        text = "Option 1"
    )
    KwikRadioOption(
        value = "option2",
        text = "Option 2"
    )
    KwikRadioOption(
        value = "option3",
        text = "Option 3"
    )
}
```

---

### Rating Bars
Implement rating input with customizable rating bars. Supports different icons and sizes.

![Rating Bar Light Mode](media/ratingbar-light.png) ![Rating Bar Dark Mode](media/ratingbar-night.png)

```kotlin
var rating by remember { mutableStateOf(3.5f) }

KwikRatingBar(
    rating = rating,
    onRatingChanged = { rating = it },
    maxRating = 5,
    iconSize = 24.dp
)
```

---

### Progress Bars
Display progress indicators with various styles and customization options, including linear and circular progress bars.

![Progress Bar Light Mode](media/progressbar-light.png) ![Progress Bar Dark Mode](media/progressbar-night.png)

```kotlin
// Linear progress bar
KwikLinearProgressBar(
    progress = 0.75f,
    color = MaterialTheme.colorScheme.primary
)

// Circular progress bar
KwikCircularProgressBar(
    progress = 0.75f,
    color = MaterialTheme.colorScheme.primary,
    size = 48.dp
)

// Indeterminate progress bar
KwikLinearProgressBar(
    isIndeterminate = true
)
```

---

## Installation

Add KwikUI to your project by including the following in your app's `build.gradle` file:

```kotlin
dependencies {
    implementation("com.isakaro:kwikui:1.0.0")
}
```

## Documentation

For detailed documentation and more examples, visit our [official documentation](https://kwikui.dev/docs).

## License

KwikUI is available under the MIT license. See the LICENSE file for more info.