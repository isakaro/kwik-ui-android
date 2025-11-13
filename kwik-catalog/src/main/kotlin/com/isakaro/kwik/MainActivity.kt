package com.isakaro.kwik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Alignment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.isakaro.kwik.catalog.accordion.KwikAccordionScreen
import com.isakaro.kwik.catalog.avatar.KwikAvatarScreen
import com.isakaro.kwik.catalog.biometrics.KwikBiometricsScreen
import com.isakaro.kwik.catalog.bottomtabs.KwikBottomTabsScreen
import com.isakaro.kwik.catalog.bottomtabs.KwikBottomTabsScreenStyled
import com.isakaro.kwik.catalog.button.KwikButtonScreen
import com.isakaro.kwik.catalog.card.KwikCardScreen
import com.isakaro.kwik.catalog.carousel.KwikCarouselScreen
import com.isakaro.kwik.catalog.checkbox.KwikCheckBoxScreen
import com.isakaro.kwik.catalog.colors.KwikColorsScreen
import com.isakaro.kwik.catalog.countrypicker.KwikCountryPickerScreen
import com.isakaro.kwik.catalog.date.KwikDateScreen
import com.isakaro.kwik.catalog.dialog.KwikDialogScreen
import com.isakaro.kwik.catalog.dropdown.KwikDropDownScreen
import com.isakaro.kwik.catalog.filterchip.KwikFilterChipScreen
import com.isakaro.kwik.catalog.grid.KwikGridScreen
import com.isakaro.kwik.catalog.permission.KwikPermissionsScreen
import com.isakaro.kwik.catalog.progress.KwikProgressIndicatorScreen
import com.isakaro.kwik.catalog.radio.KwikRadioButtonScreen
import com.isakaro.kwik.catalog.rating.KwikRatingBarScreen
import com.isakaro.kwik.catalog.searchview.KwikSearchViewScreen
import com.isakaro.kwik.catalog.shape.KwikShapeScreen
import com.isakaro.kwik.catalog.slider.KwikSliderScreen
import com.isakaro.kwik.catalog.stepper.KwikStepperScreen
import com.isakaro.kwik.catalog.switch.KwikSwitchScreen
import com.isakaro.kwik.catalog.tabs.KwikTabScreen
import com.isakaro.kwik.catalog.tagsinput.KwikTagsInputScreen
import com.isakaro.kwik.catalog.textfield.KwikOutlinedTextFieldScreen
import com.isakaro.kwik.catalog.textfield.KwikTextFieldScreen
import com.isakaro.kwik.catalog.timeline.KwikTimelineScreen
import com.isakaro.kwik.catalog.toast.KwikToastScreen
import com.isakaro.kwik.catalog.typography.KwikTypographyScreen
import com.isakaro.kwik.catalog.webview.KwikWebViewScreen
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.KwikAccordionScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikAvatarScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikBiometricsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikBottomTabsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikBottomTabsScreenStyledDestination
import com.ramcosta.composedestinations.generated.destinations.KwikButtonScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikCardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikCarouselScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikCheckBoxScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikColorsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikComponentsCatalogScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikCountryPickerScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikDateScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikDialogScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikDropDownScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikFilterChipScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikGridScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikOutlinedTextFieldScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikPermissionsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikProgressIndicatorScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikRadioButtonScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikRatingBarScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikSearchViewScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikShapeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikSliderScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikStepperScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikSwitchScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikTabScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikTagsInputScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikTextFieldScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikTimelineScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikToastScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikTypographyScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KwikWebViewScreenDestination
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.rememberNavHostEngine

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val installedSplashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            val engine = rememberNavHostEngine(
                navHostContentAlignment = Alignment.TopCenter
            )

            val navController = engine.rememberNavController()

            KwikTheme {
                ProvideNavigator(navController) {
                    DestinationsNavHost(
                        engine = engine,
                        navGraph = NavGraphs.root,
                        navController = navController,
                        start = KwikComponentsCatalogScreenDestination
                    ) {
                        composable(KwikComponentsCatalogScreenDestination) {
                            KwikComponentsCatalogScreen()
                        }
                        composable(KwikAccordionScreenDestination) {
                            KwikAccordionScreen()
                        }
                        composable(KwikButtonScreenDestination) {
                            KwikButtonScreen()
                        }
                        composable(KwikBottomTabsScreenDestination) {
                            KwikBottomTabsScreen()
                        }
                        composable(KwikBottomTabsScreenStyledDestination){
                            KwikBottomTabsScreenStyled()
                        }
                        composable(KwikCardScreenDestination) {
                            KwikCardScreen()
                        }
                        composable(KwikCheckBoxScreenDestination) {
                            KwikCheckBoxScreen()
                        }
                        composable(KwikDialogScreenDestination) {
                            KwikDialogScreen()
                        }
                        composable(KwikDropDownScreenDestination) {
                            KwikDropDownScreen()
                        }
                        composable(KwikTabScreenDestination) {
                            KwikTabScreen()
                        }
                        composable(KwikProgressIndicatorScreenDestination) {
                            KwikProgressIndicatorScreen()
                        }
                        composable(KwikRadioButtonScreenDestination) {
                            KwikRadioButtonScreen()
                        }
                        composable(KwikSliderScreenDestination) {
                            KwikSliderScreen()
                        }
                        composable(KwikToastScreenDestination) {
                            KwikToastScreen()
                        }
                        composable(KwikSwitchScreenDestination) {
                            KwikSwitchScreen()
                        }
                        composable(KwikOutlinedTextFieldScreenDestination) {
                            KwikOutlinedTextFieldScreen()
                        }
                        composable(KwikTextFieldScreenDestination) {
                            KwikTextFieldScreen()
                        }
                        composable(KwikAccordionScreenDestination) {
                            KwikAccordionScreen()
                        }
                        composable(KwikPermissionsScreenDestination) {
                            KwikPermissionsScreen()
                        }
                        composable(KwikRatingBarScreenDestination){
                            KwikRatingBarScreen()
                        }
                        composable(KwikCountryPickerScreenDestination){
                            KwikCountryPickerScreen()
                        }
                        composable(KwikSearchViewScreenDestination){
                            KwikSearchViewScreen()
                        }
                        composable(KwikStepperScreenDestination){
                            KwikStepperScreen()
                        }
                        composable(KwikFilterChipScreenDestination){
                            KwikFilterChipScreen()
                        }
                        composable(KwikCarouselScreenDestination){
                            KwikCarouselScreen()
                        }
                        composable(KwikAvatarScreenDestination){
                            KwikAvatarScreen()
                        }
                        composable(KwikTypographyScreenDestination){
                            KwikTypographyScreen()
                        }
                        composable(KwikColorsScreenDestination){
                            KwikColorsScreen()
                        }
                        composable(KwikShapeScreenDestination){
                            KwikShapeScreen()
                        }
                        composable(KwikWebViewScreenDestination){
                            KwikWebViewScreen()
                        }
                        composable(KwikTimelineScreenDestination){
                            KwikTimelineScreen()
                        }
                        composable(KwikDateScreenDestination){
                            KwikDateScreen()
                        }
                        composable(KwikTagsInputScreenDestination){
                            KwikTagsInputScreen()
                        }
                        composable(KwikBiometricsScreenDestination){
                            KwikBiometricsScreen()
                        }
                        composable(KwikGridScreenDestination){
                            KwikGridScreen()
                        }
                    }
                }
            }
        }
    }
}