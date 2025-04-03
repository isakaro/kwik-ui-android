package com.isakaro.Kwik.catalog.appbar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.Kwik.KwikAppBar
import com.isakaro.Kwik.catalog.ShowCase
import com.isakaro.Kwik.navigator
import com.isakaro.Kwik.theme.Theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination
internal fun KwikAppBarScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    ShowCase(title = "AppBar") {
        Scaffold(
            topBar = {
                KwikAppBar(
                    title = "This is a title",
                    subtitle = "Subtitle",
                    navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                ){
                    navigator.navigateUp()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(start = 12.dp, end = 12.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ){

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStartScreen() {
    KwikTheme {
        KwikAppBarScreen()
    }
}