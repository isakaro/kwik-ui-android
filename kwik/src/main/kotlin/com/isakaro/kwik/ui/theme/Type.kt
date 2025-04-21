package com.isakaro.kwik.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.isakaro.kwik.catalog.R

private val KwikFont = FontFamily(
    Font(
        resId = R.font.font_normal,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.font_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.font_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.font_bold,
        weight = FontWeight.Bold
    ),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = KwikFont,
        fontSize = 52.sp,
        fontWeight = FontWeight.Normal
    ),
    displayMedium = TextStyle(
        fontFamily = KwikFont,
        fontSize = 42.sp,
        fontWeight = FontWeight.Normal
    ),
    displaySmall = TextStyle(
        fontFamily = KwikFont,
        fontSize = 30.sp,
        fontWeight = FontWeight.Normal
    ),
    headlineLarge = TextStyle(
        fontFamily = KwikFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        fontFamily = KwikFont,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = KwikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = KwikFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        fontFamily = KwikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleSmall = TextStyle(
        fontFamily = KwikFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),
    bodyLarge = TextStyle(
        fontFamily = KwikFont,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontFamily = KwikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontFamily = KwikFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    labelLarge = TextStyle(
        fontFamily = KwikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    labelMedium = TextStyle(
        fontFamily = KwikFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    labelSmall = TextStyle(
        fontFamily = KwikFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
)