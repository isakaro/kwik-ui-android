package com.isakaro.ui.catalog.navigator

sealed class NavigationRoute(
    val route: String
) {
    data object StartScreen : NavigationRoute("start")

    data object ButtonScreen : NavigationRoute("button")

    data object AppBarScreen : NavigationRoute("appBar")

    data object BottomNavScreen : NavigationRoute("bottomNav")

    data object CardScreen : NavigationRoute("card")

    data object CheckBoxScreen : NavigationRoute("checkBox")

    data object DialogScreen : NavigationRoute("dialog")

    data object DropDownScreen : NavigationRoute("dropDown")

    data object TabScreen : NavigationRoute("tab")

    data object BottomSheetScreen : NavigationRoute("bottomSheet")

    data object ProgressIndicatorScreen : NavigationRoute("progressIndicator")

    data object RadioButtonScreen : NavigationRoute("radioButton")

    data object SliderScreen : NavigationRoute("slider")

    data object SnackBarScreen : NavigationRoute("snackBar")

    data object SwitchScreen : NavigationRoute("switch")

    data object TextFieldScreen : NavigationRoute("textField")

    data object ColorScreen : NavigationRoute("color")

    data object TypographyScreen : NavigationRoute("typography")

    data object ShapeScreen : NavigationRoute("shape")

    data object AccordionScreen : NavigationRoute("accordion")

    data object PermissionScreen : NavigationRoute("permissions")
}
