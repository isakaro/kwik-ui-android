package com.isakaro.qwik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isakaro.appcatalog.StartScreen
import com.isakaro.qwik.catalog.accordion.QwikAccordionScreen
import com.isakaro.qwik.catalog.appbar.AppBarScreen
import com.isakaro.qwik.catalog.bottomsheet.BottomSheetScreen
import com.isakaro.qwik.catalog.button.ButtonScreen
import com.isakaro.qwik.catalog.card.CardScreen
import com.isakaro.qwik.catalog.checkbox.CheckBoxScreen
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
                        AppBarScreen()
                    }
                    composable(NavigationRoute.ButtonScreen.route) {
                        ButtonScreen()
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
                    composable(NavigationRoute.SnackBarScreen.route) {
                        SnackBarScreen()
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
                    themeNavigation()
                }
            }
        }
    }

    private fun NavGraphBuilder.themeNavigation() {
        composable(NavigationRoute.ColorScreen.route) {
            ColorScreen()
        }
        composable(NavigationRoute.TypographyScreen.route) {
            TypographyScreen()
        }
        composable(NavigationRoute.ShapeScreen.route) {
            ShapeScreen()
        }
    }
}