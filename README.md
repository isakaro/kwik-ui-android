![KwikUI Logo](media/KwikUI.png)

# KwikUI

**KwikUI** is a comprehensive Android UI library designed to **accelerate your development process** by providing pre-built, customizable UI components. Spend less time on boilerplate and more time focusing on your app's core functionality.

---

## ✨ Features

### 🔘 Buttons

Easily create various button styles with customizable colors, shapes, and sizes. Supports states like:

- Enabled
- Disabled
- Loading

| Light Mode | Dark Mode |
|------------|-----------|
| ![button-light](media/button-light.png) | ![button-night](media/button-night.png) |

```kotlin
KwikButton(
    text = "Submit",
    onClick = { /* handle click */ },
    isLoading = false,
    isEnabled = true
)
```