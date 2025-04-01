package com.isakaro.qwik

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IsakaroLoadingView(
    text: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, trackColor = Color.LightGray)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            color = Color.Gray,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun IsakaroCircularLoading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        trackColor = Color.LightGray
    )
}

@Composable
@Preview(showBackground = true)
fun IsakaroLoadingViewPreview() {
    IsakaroLoadingView(
        text = "Loading... Please Wait..."
    )
}

@Composable
@Preview(showBackground = true)
fun IsakaroLoadingPreview() {
    IsakaroCircularLoading()
}