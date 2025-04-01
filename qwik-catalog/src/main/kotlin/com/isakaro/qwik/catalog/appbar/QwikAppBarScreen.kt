package com.isakaro.qwik.catalog.appbar

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
import com.isakaro.qwik.QwikAppBar
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.theme.Theme.QwikTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun QwikAppBarScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    ShowCaseContainer {
        ShowCase(title = "BottomAppBar") {
            Scaffold(
                topBar = {
                    QwikAppBar(
                        title = "This is a title",
                        subtitle = "Subtitle",
                        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    ){

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
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    QwikTheme {
        QwikAppBarScreen()
    }
}
