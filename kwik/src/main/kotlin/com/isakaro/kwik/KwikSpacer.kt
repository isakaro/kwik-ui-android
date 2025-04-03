package com.isakaro.kwik

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KwikVSpacer(
    height: Int
) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
fun KwikHSpacer(
    width: Int
) {
    Spacer(modifier = Modifier.height(width.dp))
}
