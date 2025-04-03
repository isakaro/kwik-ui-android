package com.isakaro.qwik

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.isakaro.qwik.theme.QwikColorWarning
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

enum class QwikToastType {
    NEUTRAL,
    WARNING,
    SUCCESS,
    ERROR
}

data class QwikToastState(
    val id: UUID = UUID.randomUUID(),
    val message: String = "",
    val type: QwikToastType = QwikToastType.NEUTRAL,
    val isVisible: Boolean = false,
    val duration: Long = 4000L
)

@Composable
fun rememberQwikToastState(): MutableState<QwikToastState> {
    return rememberSaveable { mutableStateOf(QwikToastState()) }
}

/**
 * A modern toast component that can be used to display messages to the user.
 * @param state: [QwikToastState] The state of the toast.
 * @param modifier: The modifier for the toast.
 *
 * Example usage:
 *
 * ```
 * val toastState = rememberQwikToastState()
 *
 * QwikToast(state = toastState)
 *
 * // Show a toast
 * toastState.showToast("This is a toast message")
 * ```
 *
 * ```
 * // show success toast
 * toastState.showToast("This is a success toast", QwikToastType.SUCCESS)
 * ```
 *
 * ```
 * // show warning toast
 * toastState.showToast("This is a warning toast", QwikToastType.WARNING)
 * ```
 *
 * ```
 * // show with duration
 * toastState.showToast("This is a 6 seconds toast", 6000L) // 6 seconds
 * ```
 **/
@Composable
fun QwikToast(
    state: MutableState<QwikToastState>,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    // Animation progress state
    val progress = remember { Animatable(1f) }

    val backgroundColor = when (state.value.type) {
        QwikToastType.NEUTRAL -> MaterialTheme.colorScheme.primary
        QwikToastType.WARNING -> QwikColorWarning
        QwikToastType.SUCCESS -> Color.Green
        QwikToastType.ERROR -> MaterialTheme.colorScheme.error
    }

    val icon = when (state.value.type) {
        QwikToastType.NEUTRAL -> Icons.Default.Info
        QwikToastType.WARNING -> Icons.Default.Warning
        QwikToastType.SUCCESS -> Icons.Default.Check
        QwikToastType.ERROR -> Icons.Default.Info
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
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress.value)
                            .height(4.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
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
 * @param type: The type of toast to display. Can be [QwikToastType.NEUTRAL], [QwikToastType.WARNING], [QwikToastType.SUCCESS], [QwikToastType.ERROR]. Default is [QwikToastType.NEUTRAL], which uses the primary color from [MaterialTheme.colorScheme].
 * @param duration: The duration to display the toast. Default is 4 seconds.
 * */
fun MutableState<QwikToastState>.showToast(
    message: String,
    type: QwikToastType = QwikToastType.NEUTRAL,
    duration: Long = 4000L
) {
    this.value = QwikToastState(
        message = message,
        duration = duration,
        type = type,
        isVisible = true
    )
}