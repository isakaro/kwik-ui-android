package com.isakaro.kwik

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}

@Composable
fun navigator(): DestinationsNavigator {
    val navController = LocalNavController.current
    return navController.rememberDestinationsNavigator()
}

@Composable
fun ProvideNavigator(
    navigator: NavHostController,
    content: @Composable () -> Unit
) {
    val navController = LocalNavController provides navigator

    CompositionLocalProvider(navController) {
        content()
    }
}