package com.isakaro.kwik.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.catalog.appabr.KwikAppBar
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.ui.text.KwikText

@Composable
internal fun ShowCase(
    title: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KwikVSpacer(16)

        if(title != null){
            KwikText.TitleMedium(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        KwikVSpacer(4)

        content()

        KwikVSpacer(16)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ShowCaseContainer(
    title: String,
    onBackClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) = Surface(
    modifier = Modifier.fillMaxSize()
) {
    Scaffold(
        topBar = {
            KwikAppBar(
                title = title,
                navigationClick = {
                    onBackClick()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScrollableShowCaseContainer(
    title: String,
    onBackClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) = Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
) {

    Scaffold(
        topBar = {
            KwikAppBar(
                title = title,
                navigationClick = {
                    onBackClick()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}
