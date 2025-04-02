package com.isakaro.qwik

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.theme.QwikColorWarning
import com.isakaro.qwik.theme.QwikColorSuccess
import com.isakaro.qwik.utils.QwikNetworkUtils
import kotlinx.coroutines.delay

/**
 * Useful for displaying the current network state of the app.
 *
 * @param networkState [QwikNetworkUtils.NetworkState] The current network state of the app.
 * @param modifier The modifier for the view.
 * */
@Composable
fun QwikNetworkStateView(
    networkState: QwikNetworkUtils.NetworkState,
    modifier: Modifier = Modifier
) {
    var showGreenIndicator by remember { mutableStateOf(false) }
    var previousNetworkState by remember { mutableStateOf<QwikNetworkUtils.NetworkState?>(null) }

    LaunchedEffect(networkState) {
        if (networkState is QwikNetworkUtils.NetworkState.Available &&
            (previousNetworkState is QwikNetworkUtils.NetworkState.Unavailable || previousNetworkState is QwikNetworkUtils.NetworkState.Reconnecting)
        ) {
            showGreenIndicator = true
            delay(2000)
            showGreenIndicator = false
        }
        previousNetworkState = networkState
    }

    AnimatedVisibility(
        visible = networkState is QwikNetworkUtils.NetworkState.Unavailable ||
                networkState is QwikNetworkUtils.NetworkState.Reconnecting ||
                showGreenIndicator,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    when {
                        networkState is QwikNetworkUtils.NetworkState.Unavailable -> Color.Gray
                        networkState is QwikNetworkUtils.NetworkState.Reconnecting -> QwikColorWarning
                        showGreenIndicator -> QwikColorSuccess
                        else -> Color.Transparent
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(networkState is QwikNetworkUtils.NetworkState.Reconnecting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White
                )
            }
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                text = when (networkState) {
                    is QwikNetworkUtils.NetworkState.Available -> "Connected"
                    is QwikNetworkUtils.NetworkState.Unavailable -> "No internet connection"
                    is QwikNetworkUtils.NetworkState.Reconnecting -> "Reconnecting..."
                    else -> ""
                },
                color = Color.White
            )
        }
    }
}