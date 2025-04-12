package com.isakaro.kwik.loading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.spacer.KwikVSpacer
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.theme.KwikTheme

@Composable
fun KwikLoadingView(
    text: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        KwikCircularLoading(
            color = MaterialTheme.colorScheme.primary
        )

        KwikVSpacer(8)

        KwikText.TitleSmall(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun KwikCircularLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = Color.Transparent
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor
    )
}

@Composable
@Preview(showBackground = true)
private fun KwikLoadingViewPreview() {
    KwikTheme {
        KwikLoadingView(
            text = "Loading... Please Wait..."
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun KwikLoadingPreview() {
    KwikTheme {
        KwikCircularLoading()
    }
}