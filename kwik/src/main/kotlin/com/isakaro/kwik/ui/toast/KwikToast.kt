package com.isakaro.kwik.ui.toast

import androidx.annotation.RestrictTo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.isakaro.kwik.ui.theme.KwikColorSuccess
import com.isakaro.kwik.ui.theme.KwikColorWarning
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

enum class KwikToastType {
    NEUTRAL,
    WARNING,
    SUCCESS,
    ERROR
}

/**
 * The state of the toast.
 *
 * @param id: The id of the toast. Used to identify the toast. Set automatically. Don't change it.
 * @param message: The message to display in the toast.
 * @param type: The type of toast to display. Can be [KwikToastType.NEUTRAL], [KwikToastType.WARNING], [KwikToastType.SUCCESS], [KwikToastType.ERROR]. Default is [KwikToastType.NEUTRAL], which uses the primary color from [MaterialTheme.colorScheme].
 * @param isVisible: Whether the toast is visible or not.
 * @param duration: The duration to display the toast. Default is 4 seconds.
 * @param backgroundColor: The background color of the toast. Default is [MaterialTheme.colorScheme.primary].
 * */
@RestrictTo(RestrictTo.Scope.LIBRARY)
data class KwikToastState(
    val id: UUID = UUID.randomUUID(),
    val message: String = "",
    val type: KwikToastType = KwikToastType.NEUTRAL,
    val isVisible: Boolean = false,
    val duration: Long = 4000L,
    val backgroundColor: Color = Color.Unspecified,
)

/**
 * A state holder for the toast. Refer to [KwikToastState]
 * */
@Composable
fun rememberKwikToastState(): MutableState<KwikToastState> {
    return remember { mutableStateOf(KwikToastState()) }
}

/**
 * A modern toast component that can be used to display messages to the user.
 * @param state: [KwikToastState] The state of the toast.
 * @param modifier: The modifier for the toast.
 *
 * Example usage:
 *
 * ```
 * val toastState = rememberKwikToastState()
 *
 * KwikToast(state = toastState)
 *
 * // Show a toast
 * toastState.showToast("This is a toast message")
 * ```
 *
 * ```
 * // show success toast
 * toastState.showToast("This is a success toast", KwikToastType.SUCCESS)
 * ```
 *
 * ```
 * // show warning toast
 * toastState.showToast("This is a warning toast", KwikToastType.WARNING)
 * ```
 *
 * ```
 * // show with duration
 * toastState.showToast("This is a 6 seconds toast", 6000L) // 6 seconds
 * ```
 **/
@Composable
fun KwikToast(
    state: MutableState<KwikToastState>,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    // Animation progress state
    val progress = remember { Animatable(1f) }

    val backgroundColor = if(state.value.backgroundColor == Color.Unspecified) {
        when (state.value.type) {
            KwikToastType.NEUTRAL -> MaterialTheme.colorScheme.primary
            KwikToastType.WARNING -> KwikColorWarning
            KwikToastType.SUCCESS -> KwikColorSuccess
            KwikToastType.ERROR -> MaterialTheme.colorScheme.error
        }
    } else {
        state.value.backgroundColor
    }

    val icon = when (state.value.type) {
        KwikToastType.NEUTRAL -> Icons.Default.Info
        KwikToastType.WARNING -> Icons.Default.Warning
        KwikToastType.SUCCESS -> Icons.Default.Check
        KwikToastType.ERROR -> Icons.Default.Info
    }

    if (state.value.isVisible) {
        LaunchedEffect(state.value) {
            // Reset progress when new message appears
            progress.snapTo(1f)

            // Animate progress bar
            launch {
                progress.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = state.value.duration.toInt(),
                        easing = LinearEasing
                    )
                )
            }

            delay(state.value.duration)
            state.value = state.value.copy(isVisible = false)
        }
    }

    Popup(
        alignment = Alignment.BottomCenter,
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        AnimatedVisibility(
            visible = state.value.isVisible,
            enter = fadeIn() + slideInVertically { -it },
            exit = fadeOut() + slideOutVertically { -it }
        ) {
            Box(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 1.dp)
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress.value)
                            .height(4.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                            )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = state.value.message,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/**
 * Function to show a toast message.
 * @param message: The message to display.
 * @param type: The type of toast to display. Can be [KwikToastType.NEUTRAL], [KwikToastType.WARNING], [KwikToastType.SUCCESS], [KwikToastType.ERROR]. Default is [KwikToastType.NEUTRAL], which uses the primary color from [MaterialTheme.colorScheme].
 * @param duration: The duration to display the toast. Default is 4 seconds.
 * */
fun MutableState<KwikToastState>.showToast(
    message: String,
    type: KwikToastType = KwikToastType.NEUTRAL,
    duration: Long = 4000L,
    backgroundColor: Color = Color.Unspecified
) {
    this.value = KwikToastState(
        message = message,
        duration = duration,
        type = type,
        isVisible = true,
        backgroundColor = backgroundColor
    )
}