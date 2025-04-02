package com.isakaro.qwik.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.QwikText
import com.isakaro.qwik.QwikVSpacer

@Composable
fun ShowCase(title: String, content: @Composable BoxScope.() -> Unit) {
    QwikVSpacer(16)

    QwikText.TitleText(
        style = MaterialTheme.typography.titleMedium,
        text = title,
        color = Color.Black
    )

    Box(modifier = Modifier.padding(16.dp)) {
        content()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray)
    )
}

@Composable
fun ShowCaseContainer(content: @Composable ColumnScope.() -> Unit) = Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun ScrollableShowCaseContainer(content: @Composable ColumnScope.() -> Unit) = Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}
