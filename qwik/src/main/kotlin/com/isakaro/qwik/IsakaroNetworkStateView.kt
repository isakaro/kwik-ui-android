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
import com.isakaro.networking.utils.NetworkUtils
import com.isakaro.qwik.theme.SuccessColor
import com.isakaro.qwik.theme.WarningColor
import kotlinx.coroutines.delay

@Composable
fun QwikNetworkStateView(
    networkState: NetworkUtils.NetworkState,
    modifier: Modifier = Modifier
) {
    var showGreenIndicator by remember { mutableStateOf(false) }
    var previousNetworkState by remember { mutableStateOf<NetworkUtils.NetworkState?>(null) }

    LaunchedEffect(networkState) {
        if (networkState is NetworkUtils.NetworkState.Available &&
            (previousNetworkState is NetworkUtils.NetworkState.Unavailable || previousNetworkState is NetworkUtils.NetworkState.Reconnecting)
        ) {
            showGreenIndicator = true
            delay(2000)
            showGreenIndicator = false
        }
        previousNetworkState = networkState
    }

    AnimatedVisibility(
        visible = networkState is NetworkUtils.NetworkState.Unavailable ||
                networkState is NetworkUtils.NetworkState.Reconnecting ||
                showGreenIndicator,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    when {
                        networkState is NetworkUtils.NetworkState.Unavailable -> Color.Gray
                        networkState is NetworkUtils.NetworkState.Reconnecting -> WarningColor
                        showGreenIndicator -> SuccessColor
                        else -> Color.Transparent
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(networkState is NetworkUtils.NetworkState.Reconnecting) {
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
                    is NetworkUtils.NetworkState.Available -> "Connected"
                    is NetworkUtils.NetworkState.Unavailable -> "No internet connection"
                    is NetworkUtils.NetworkState.Reconnecting -> "Reconnecting..."
                    else -> ""
                },
                color = Color.White
            )
        }
    }
}