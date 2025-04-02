package com.isakaro.qwik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isakaro.qwik.catalog.accordion.QwikAccordionScreen
import com.isakaro.qwik.catalog.appbar.QwikAppBarScreen
import com.isakaro.qwik.catalog.bottomsheet.BottomSheetScreen
import com.isakaro.qwik.catalog.button.QwikButtonScreen
import com.isakaro.qwik.catalog.card.CardScreen
import com.isakaro.qwik.catalog.checkbox.CheckBoxScreen
import com.isakaro.qwik.catalog.daterangepicker.QwikDateRangePickerScreen
import com.isakaro.qwik.catalog.dialog.DialogScreen
import com.isakaro.qwik.catalog.dropdown.DropDownScreen
import com.isakaro.qwik.catalog.navigation.BottomNavScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute
import com.isakaro.qwik.catalog.permission.PermissionsScreen
import com.isakaro.qwik.catalog.progress.ProgressIndicatorScreen
import com.isakaro.qwik.catalog.radio.RadioButtonScreen
import com.isakaro.qwik.catalog.slider.SliderScreen
import com.isakaro.qwik.catalog.switch.SwitchScreen
import com.isakaro.qwik.catalog.tabs.TabScreen
import com.isakaro.qwik.catalog.textfield.TextFieldScreen
import com.isakaro.qwik.catalog.toast.QwikToastScreen
import com.isakaro.qwik.theme.Theme.QwikTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QwikTheme {
                val navHostController = rememberNavController()

                NavHost(navController = navHostController, startDestination = NavigationRoute.StartScreen.route) {
                    composable(NavigationRoute.StartScreen.route) {
                        StartScreen(
                            navClick = { nav -> navHostController.navigate(route = nav.route) }
                        )
                    }
                    composable(NavigationRoute.AppBarScreen.route) {
                        QwikAppBarScreen()
                    }
                    composable(NavigationRoute.DateRangePicker.route) {
                        QwikDateRangePickerScreen()
                    }
                    composable(NavigationRoute.ButtonScreen.route) {
                        QwikButtonScreen()
                    }
                    composable(NavigationRoute.BottomNavScreen.route) {
                        BottomNavScreen()
                    }
                    composable(NavigationRoute.CardScreen.route) {
                        CardScreen()
                    }
                    composable(NavigationRoute.CheckBoxScreen.route) {
                        CheckBoxScreen()
                    }
                    composable(NavigationRoute.DialogScreen.route) {
                        DialogScreen()
                    }
                    composable(NavigationRoute.DropDownScreen.route) {
                        DropDownScreen()
                    }
                    composable(NavigationRoute.BottomSheetScreen.route) {
                        BottomSheetScreen()
                    }
                    composable(NavigationRoute.TabScreen.route) {
                        TabScreen()
                    }
                    composable(NavigationRoute.ProgressIndicatorScreen.route) {
                        ProgressIndicatorScreen()
                    }
                    composable(NavigationRoute.RadioButtonScreen.route) {
                        RadioButtonScreen()
                    }
                    composable(NavigationRoute.SliderScreen.route) {
                        SliderScreen()
                    }
                    composable(NavigationRoute.ToastScreen.route) {
                        QwikToastScreen()
                    }
                    composable(NavigationRoute.SwitchScreen.route) {
                        SwitchScreen()
                    }
                    composable(NavigationRoute.TextFieldScreen.route) {
                        TextFieldScreen()
                    }
                    composable(NavigationRoute.AccordionScreen.route) {
                        QwikAccordionScreen()
                    }
                    composable(NavigationRoute.PermissionScreen.route) {
                        PermissionsScreen()
                    }
                }
            }
        }
    }
}