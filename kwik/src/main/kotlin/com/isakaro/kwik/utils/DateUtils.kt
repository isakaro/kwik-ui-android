package com.isakaro.kwik.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Date.toMMdd(): String {
    val formatter = SimpleDateFormat("MMM dd")
    val formattedDate: String = formatter.format(this)
    return formattedDate
}