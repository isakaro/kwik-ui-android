package com.isakaro.ui.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import java.time.Duration

@SuppressLint("DefaultLocale")
@Composable
fun IsakaroCountDownTimer(
    time: Duration,
    onTimeUpdate: (String) -> Unit,
    onTimerFinished: () -> Unit
) {
    var timer by remember { mutableLongStateOf(time.toMillis()) }

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