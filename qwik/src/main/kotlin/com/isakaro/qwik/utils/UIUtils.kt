package com.isakaro.qwik.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue

val MutableState<TextFieldValue>.text get() = this.value.text

tailrec fun Context.activity(): Activity = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.activity()
    else -> throw IllegalStateException()
}