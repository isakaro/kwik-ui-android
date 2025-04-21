package com.isakaro.kwik.ui.countdown

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

/**
 * A countdown timer that displays the time remaining in the format "mm:ss".
 *
 * @param timeMillis The total time in milliseconds to count down from.
 * @param onTimeUpdate The callback that will be called every second with the current time remaining.
 * @param onTimerFinished The callback that will be called when the timer reaches 0.
 *
 * <p>
 * You can use it to display a countdown timer in your Composable.
 * For example, you can use it to display a countdown timer in a quiz app. Or to wait for an OTP to be sent before allowing the user to resend it.
 * </p>
 *
 * Example usage:
 *
 * ```
 * KwikCountDownTimer(
 *    timeMillis = 60000L,
 *    onTimeUpdate = { time ->
 *      Text(time)
 *    },
 *    onTimerFinished = {
 *      Text("Timer finished")
 *    }
 * )
 *    ```
 * */
@SuppressLint("DefaultLocale")
@Composable
fun KwikCountDownTimer(
    timeMillis: Long,
    onTimeUpdate: (String) -> Unit,
    onTimerFinished: () -> Unit
) {
    var timer by remember { mutableLongStateOf(timeMillis) }

    LaunchedEffect(timer) {
        val minutes = (timer / 1000) / 60
        val seconds = (timer / 1000) % 60
        onTimeUpdate("$minutes:${String.format("%02d", seconds)}")

        while (timer > 0) {
            delay(1000L)
            timer -= 1000L
            val currentMinutes = (timer / 1000) / 60
            val currentSeconds = (timer / 1000) % 60
            onTimeUpdate("$currentMinutes:${String.format("%02d", currentSeconds)}")
        }
        onTimerFinished()
    }
}

@Composable
fun KwikCountDownTimer(
    minutes: Int,
    seconds: Int,
    onTimeUpdate: (String) -> Unit,
    onTimerFinished: () -> Unit
) {
    val totalMillis = ((minutes * 60) + seconds) * 1000L
    KwikCountDownTimer(totalMillis, onTimeUpdate, onTimerFinished)
}