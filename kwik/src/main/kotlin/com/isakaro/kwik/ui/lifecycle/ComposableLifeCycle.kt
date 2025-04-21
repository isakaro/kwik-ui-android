package com.isakaro.kwik.ui.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * Helper component to observe the lifecycle of the current [LifecycleOwner] and call the provided
 * [onResume] and [onPause] callbacks when the lifecycle events occur.
 * This is useful for components that need to perform actions when the Composable is resumed or paused.
 *
 * Be aware that the [onResume] callback will be called when the Composable is first created.
 * Also make sure that whatever you do in the [onResume] and [onPause] callbacks is idempotent. Otherwise you might perform an expensive operation multiple times.
 * Imagine an API call in the [onResume] callback, if the Composable is recomposed, which can happen multiple times within a second, the API call will be made again.
 *
 * @param onResume Callback to be called when the Composable is resumed.
 * @param onPause Callback to be called when the Composable is paused.
 * */
@Composable
fun KwikComposableLifeCycle(
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onStarted: () -> Unit = {},
) {

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)
    val currentOnStarted by rememberUpdatedState(onStarted)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    currentOnStarted()
                }
                Lifecycle.Event.ON_RESUME -> {
                    currentOnResume()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    currentOnPause()
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

}
