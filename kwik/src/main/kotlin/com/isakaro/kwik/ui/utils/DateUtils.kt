package com.isakaro.kwik.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun OffsetDateTime.toFormat(format: String = "MMM dd", locale: Locale = Locale.ENGLISH): String {
    val formatter = DateTimeFormatter.ofPattern(format, locale)
    return formatter.format(this)
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.toFormat(format: String = "MMM dd", locale: Locale = Locale.ENGLISH): String {
    val formatter = DateTimeFormatter.ofPattern(format, locale)
    return formatter.format(this)
}

fun LocalDate.toMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return this.atStartOfDay(zoneId).toInstant().toEpochMilli()
}