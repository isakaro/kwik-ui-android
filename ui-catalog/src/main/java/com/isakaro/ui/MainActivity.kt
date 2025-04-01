package com.isakaro.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.isakaro.appcatalog.StartScreen
import com.isakaro.ui.catalog.accordion.AmpersandAccordionScreen
import com.isakaro.ui.catalog.appbar.AppBarScreen
import com.isakaro.ui.catalog.bottomsheet.BottomSheetScreen
import com.isakaro.ui.catalog.button.ButtonScreen
import com.isakaro.ui.catalog.card.CardScreen
import com.isakaro.ui.catalog.checkbox.CheckBoxScreen
import com.isakaro.ui.catalog.dialog.DialogScreen
import com.isakaro.ui.catalog.dropdown.DropDownScreen
import com.isakaro.ui.catalog.navigation.BottomNavScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute
import com.isakaro.ui.catalog.permission.PermissionsScreen
import com.isakaro.ui.catalog.progress.ProgressIndicatorScreen
import com.isakaro.ui.catalog.radio.RadioButtonScreen
import com.isakaro.ui.catalog.slider.SliderScreen
import com.isakaro.ui.catalog.snackbar.SnackBarScreen
import com.isakaro.ui.catalog.switch.SwitchScreen
import com.isakaro.ui.catalog.tabs.TabScreen
import com.isakaro.ui.catalog.textfield.TextFieldScreen
import com.isakaro.ui.catalog.theme.ColorScreen
import com.isakaro.ui.catalog.theme.ShapeScreen
import com.isakaro.ui.catalog.theme.TypographyScreen
import app.isakaro.ui.library.theme.Theme.IsakaroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IsakaroTheme {
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
                        AmpersandAccordionScreen()
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