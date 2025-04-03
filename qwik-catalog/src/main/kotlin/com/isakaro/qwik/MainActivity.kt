package com.isakaro.qwik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Alignment
import androidx.navigation.compose.rememberNavController
import com.isakaro.qwik.destinations.ComponentsCatalogScreenDestination
import com.isakaro.qwik.theme.Theme.QwikTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.rememberNavHostEngine

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val engine = rememberNavHostEngine(
                navHostContentAlignment = Alignment.TopCenter,
                rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                defaultAnimationsForNestedNavGraph = mapOf(
                    NavGraphs.root to NestedNavGraphDefaultAnimations(
                        enterTransition = { slideInHorizontally() },
                        exitTransition = { slideOutHorizontally() }
                    ),
                )
            )

            val navHostController = rememberNavController()
            val navController = engine.rememberNavController()

            QwikTheme {
                ProvideNavigator(navController) {
                    DestinationsNavHost(
                        engine = engine,
                        navGraph = NavGraphs.root,
                        navController = navController,
                        startRoute = ComponentsCatalogScreenDestination,
                    ) {
                        composable(ComponentsCatalogScreenDestination) {
                            ComponentsCatalogScreen()
                        }
                        /*composable(NavigationRoute.AppBarScreen.route) {
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
                        composable(NavigationRoute.OutlinedTextFieldScreen.route) {
                            OutlinedTextFieldScreen()
                        }
                        composable(NavigationRoute.TextFieldScreen.route) {
                            TextFieldScreen()
                        }
                        composable(NavigationRoute.AccordionScreen.route) {
                            QwikAccordionScreen()
                        }
                        composable(NavigationRoute.PermissionScreen.route) {
                            PermissionsScreen()
                        }*/
                    }
                }
            }
        }
    }
}