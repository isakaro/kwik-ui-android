# KwikUI

A comprehensive Android UI library designed to accelerate your development process by providing pre-built, customizable UI components. Spend less time on boilerplate and more time focusing on your app's core functionality.

![KwikUI Logo](media/KwikUI.png)

## Features

### Buttons
Easily create various button styles with customizable colors, shapes, and sizes. Supports different states like enabled, disabled, and loading.

![Button Light Mode](media/button-light.png) ![Button Dark Mode](media/button-night.png)

```kotlin
@Composable
fun ButtonExample() {
    KwikButton(
        text = "Submit",
        style = KwikButtonStyle.Filled,
        cornerRadius = 8.dp,
        onClick = {
            // Handle click event
        }
    )
    
    // Loading state button
    KwikButton(
        text = "Loading",
        isLoading = true,
        style = KwikButtonStyle.Outlined
    )
}
```

### Text Fields
KwikUI offers a range of text field styles to suit your needs:
- **Filled Text Fields:** Standard text fields with a filled background.
- **Outlined Text Fields:** Text fields with an outlined border.
- **OTP Fields:** Specialized text fields for collecting One-Time Passcodes (OTP). Provides features like automatic focus and input masking.

![Text Field Light Mode](media/textfield-light.png) ![Text Field Dark Mode](media/textfield-night.png)

```kotlin
@Composable
fun TextFieldExample() {
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
}
```

### Webview
Integrate web content seamlessly with the pre-configured webview. Includes features like file upload handling, native bridge communication, Javascript support, multi-window support, progress indicators and more.

![Webview Light Mode](media/webview-light.png) ![Webview Dark Mode](media/webview-night.png)

```kotlin
@Composable
fun WebviewExample() {
    KwikWebview(
        url = "https://example.com",
        showProgressIndicator = true,
        onJavascriptMessage = { message ->
            // Handle messages from JavaScript
        },
        onPageFinished = { url ->
            // Page finished loading
        }
    )
}
```

### Permission Handlers
Simplify permission management with easy-to-use handlers. Request permissions with clear callbacks for granted and denied scenarios. Additionally, handle multiple permissions in a single request, as well as know if you can show rationale for permissions.

![Permission Handler Light Mode](media/permission-light.png) ![Permission Handler Dark Mode](media/permission-night.png)

```kotlin
@Composable
fun PermissionHandlerExample() {
    val permissionState = rememberKwikPermissionState(
        permission = android.Manifest.permission.CAMERA
    )
    
    LaunchedEffect(permissionState.status) {
        when (permissionState.status) {
            is PermissionStatus.Granted -> {
                // Permission granted
            }
            is PermissionStatus.Denied -> {
                // Permission denied
            }
        }
    }
    
    KwikButton(
        text = "Request Camera Permission",
        onClick = { permissionState.request() }
    )
    
    // Multiple permissions
    val multiplePermissions = rememberKwikMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    
    KwikButton(
        text = "Request Multiple Permissions",
        onClick = { multiplePermissions.requestAll() }
    )
}
```

### Radio Groups
Create and manage radio button groups effortlessly. Customize the appearance and handle selection changes with minimal code.

![Radio Group Light Mode](media/radiogroup-light.png) ![Radio Group Dark Mode](media/radiogroup-night.png)

```kotlin
@Composable
fun RadioGroupExample() {
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
}
```

### Rating Bars
Implement rating input with customizable rating bars. Supports different icons and sizes.

![Rating Bar Light Mode](media/ratingbar-light.png) ![Rating Bar Dark Mode](media/ratingbar-night.png)

```kotlin
@Composable
fun RatingBarExample() {
    var rating by remember { mutableStateOf(3.5f) }
    
    KwikRatingBar(
        rating = rating,
        onRatingChanged = { rating = it },
        maxRating = 5,
        iconSize = 24.dp
    )
}
```

### Progress Bars
Display progress indicators with various styles and customization options, including linear and circular progress bars.

![Progress Bar Light Mode](media/progressbar-light.png) ![Progress Bar Dark Mode](media/progressbar-night.png)

```kotlin
@Composable
fun ProgressBarExample() {
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
}
```

## All Components

KwikUI includes a comprehensive collection of components:

- **Accordion** (accordion, accordion group)
- **Appbar**
- **Avatar**
- **Biometrics**
- **Bottom Tabs**
- **Button**
- **Card**
- **Carousel** (slider)
- **Checkbox**
- **Counter**
- **Countrypicker**
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
- **Text Fields** (filled, outlined, with suggestions feature and debounce)
- **Timeline**
- **Toast** component (modern)
- **Toggle Button**
- **Webview**

## Installation

Add KwikUI to your project by including the following in your app's build.gradle file:

```kotlin
dependencies {
    implementation("com.kwikui:kwikui:1.0.0")
}
```

## Documentation

For detailed documentation and more examples, visit our [official documentation](https://kwikui.dev/docs).

## License

KwikUI is available under the MIT license. See the LICENSE file for more info.
