package com.isakaro.kwik.ui.helpers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.theme.KwikColorWarning
import com.isakaro.kwik.ui.theme.KwikColorSuccess
import com.isakaro.kwik.ui.utils.KwikNetworkUtils
import kotlinx.coroutines.delay

/**
 * Useful for displaying the current network state of the app.
 *
 * @param networkState [KwikNetworkUtils.NetworkState] The current network state of the app.
 * @param modifier The modifier for the view.
 * */
@Composable
fun KwikNetworkStateView(
    networkState: KwikNetworkUtils.NetworkState,
    modifier: Modifier = Modifier
) {
    var showGreenIndicator by remember { mutableStateOf(false) }
    var previousNetworkState by remember { mutableStateOf<KwikNetworkUtils.NetworkState?>(null) }

    LaunchedEffect(networkState) {
        if (networkState is KwikNetworkUtils.NetworkState.Available &&
            (previousNetworkState is KwikNetworkUtils.NetworkState.Unavailable || previousNetworkState is KwikNetworkUtils.NetworkState.Reconnecting)
        ) {
            showGreenIndicator = true
            delay(2000)
            showGreenIndicator = false
        }
        previousNetworkState = networkState
    }

    AnimatedVisibility(
        visible = networkState is KwikNetworkUtils.NetworkState.Unavailable ||
                networkState is KwikNetworkUtils.NetworkState.Reconnecting ||
                showGreenIndicator,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    when {
                        networkState is KwikNetworkUtils.NetworkState.Unavailable -> Color.Gray
                        networkState is KwikNetworkUtils.NetworkState.Reconnecting -> KwikColorWarning
                        showGreenIndicator -> KwikColorSuccess
                        else -> Color.Transparent
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(networkState is KwikNetworkUtils.NetworkState.Reconnecting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White
                )
            }
            KwikText.BodySmall(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                textAlign = TextAlign.Center,
                text = when (networkState) {
                    is KwikNetworkUtils.NetworkState.Available -> "Connected"
                    is KwikNetworkUtils.NetworkState.Unavailable -> "No internet connection"
                    is KwikNetworkUtils.NetworkState.Reconnecting -> "Reconnecting..."
                    else -> ""
                },
                color = Color.White
            )
        }
    }
}