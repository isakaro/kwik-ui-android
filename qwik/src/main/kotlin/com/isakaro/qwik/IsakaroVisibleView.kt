package com.isakaro.qwik.components

import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * This component tracks the visibility of the content inside it.
 * and notifies the caller when the visibility changes.
 * */
@Composable
fun IsakaroVisibleView(
    modifier: Modifier = Modifier,
    tolerance: Int = 0,
    positionOnScreen: Rect? = null,
    onVisibilityChanged: (isVisible: Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val context = LocalContext.current
    val isVisible = remember { mutableStateOf(false) }

    var componentBounds by remember { mutableStateOf<Rect?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(view, lifecycleOwner) {
        val observer = ViewTreeObserver.OnPreDrawListener {
            val location = IntArray(2)
            view.getLocationOnScreen(location)

            val screenTop = location[1]
            val screenBottom = screenTop + view.height

            componentBounds?.let { bounds ->
                val isCurrentlyVisible = bounds.bottom > screenTop && bounds.top + tolerance < screenBottom
                if (isVisible.value != isCurrentlyVisible) {
                    isVisible.value = isCurrentlyVisible
                    onVisibilityChanged(isCurrentlyVisible)
                }
            }
            true
        }

        view.viewTreeObserver.addOnPreDrawListener(observer)

        onDispose {
            view.viewTreeObserver.removeOnPreDrawListener(observer)
        }
    }

    Column(
        modifier = modifier.onGloballyPositioned { coordinates ->
            componentBounds = coordinates.boundsInWindow()
        }
    ) {
        content()
    }
}

