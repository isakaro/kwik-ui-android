package com.isakaro.kwik.ui.helpers

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
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * This component tracks the visibility of the content inside it.
 * and notifies the caller when the visibility changes.
 * The caller can provide a tolerance value to adjust the visibility.
 * For example, if the caller wants to know when the content is 50% visible on the screen,
 *
 * @param modifier: Modifier - The modifier for the component
 * @param tolerance: Int - The tolerance value to adjust the visibility
 * @param onVisibilityChanged: (isVisible: Boolean) -> Unit - The callback to notify the caller when the visibility changes
 * @param content: @Composable () -> Unit - The content to display and track visibility
 *
 * Example usage:
 *
 * ```
 * KwikViewVisibilityTracker(
 *    tolerance = 0,
 *    onVisibilityChanged = { isVisible ->
 *    // Do something when the content is visible
 *    }
 * ){
 *     // Content to track visibility for
 * }
 * */
@Composable
fun KwikViewVisibilityTracker(
    modifier: Modifier = Modifier,
    tolerance: Int = 0,
    onVisibilityChanged: (isVisible: Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
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

