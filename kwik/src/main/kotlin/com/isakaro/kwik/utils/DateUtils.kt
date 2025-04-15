package com.isakaro.kwik.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Date.toMMdd(): String {
    val formatter = SimpleDateFormat("MMM dd")
    val formattedDate: String = formatter.format(this)
    return formattedDate
}

fun Date.toFormat(
    format: String = "MMM dd"
): String {
    val formatter = SimpleDateFormat(format)
    val formattedDate: String = formatter.format(this)
    return formattedDate
}