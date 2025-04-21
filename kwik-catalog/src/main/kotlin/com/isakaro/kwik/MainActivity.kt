package com.isakaro.kwik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Alignment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import com.isakaro.kwik.destinations.KwikComponentsCatalogScreenDestination
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
import com.isakaro.kwik.destinations.KwikTypographyScreenDestination
import com.isakaro.kwik.destinations.KwikWebViewScreenDestination
import com.isakaro.kwik.ui.theme.KwikTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.rememberNavHostEngine

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val installedSplashScreen = installSplashScreen()

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

            val navController = engine.rememberNavController()

            KwikTheme {
                ProvideNavigator(navController) {
                    DestinationsNavHost(
                        engine = engine,
                        navGraph = NavGraphs.root,
                        navController = navController,
                        startRoute = KwikComponentsCatalogScreenDestination,
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