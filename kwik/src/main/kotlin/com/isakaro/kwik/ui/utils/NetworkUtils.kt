package com.isakaro.kwik.ui.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class KwikNetworkUtils(
    private val context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback = NetworkCallback()

    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Initializing)
    val networkState: StateFlow<NetworkState> = _networkState.asStateFlow()

    sealed class NetworkState {
        data object Initializing : NetworkState()
        data object Available : NetworkState()
        data object Unavailable : NetworkState()
        data object Reconnecting : NetworkState()
    }

    init {
        onActive()
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun onActive() {
        val currentConnectivity = isConnected
        _networkState.value = if (currentConnectivity) NetworkState.Available else NetworkState.Unavailable
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun onInactive() {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (_: Exception) {}
    }

    val isConnected: Boolean
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            connectivityManager?.run {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                    return when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
            return false
        }

    inner class NetworkCallback : ConnectivityManager.NetworkCallback() {
        private var reconnectJob: Job? = null

        override fun onAvailable(network: Network) {
            reconnectJob?.cancel()

            _networkState.value = NetworkState.Available
        }

        override fun onLost(network: Network) {
            _networkState.value = NetworkState.Reconnecting

            // Grace period to reconnect
            reconnectJob = coroutineScope.launch {
                delay(6000) // 6 seconds

                // At this point, we consider the network as unavailable
                if (!isConnected) {
                    _networkState.value = NetworkState.Unavailable
                }
            }
        }

        override fun onUnavailable() {
            _networkState.value = NetworkState.Unavailable
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            val isConnected = when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
            _networkState.value = if (isConnected) NetworkState.Available else NetworkState.Unavailable
        }
    }
}