package com.isakaro.kwik.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat

val MutableState<TextFieldValue>.text get() = this.value.text

tailrec fun Context.activity(): Activity = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.activity()
    else -> throw IllegalStateException()
}

fun Context.isPermissionGranted(vararg permission: String): Boolean {
    return permission.map { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }.all { it }
}

fun String?.blankIfNull(): String {
    if(this == "null" || this == null) return ""
    return this
}