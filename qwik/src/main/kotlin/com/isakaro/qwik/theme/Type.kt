package com.isakaro.qwik.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.isakaro.qwik.R

private val qwikFont = FontFamily(
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
        fontFamily = qwikFont,
        fontSize = 52.sp,
        fontWeight = FontWeight.Normal
    ),
    displayMedium = TextStyle(
        fontFamily = qwikFont,
        fontSize = 42.sp,
        fontWeight = FontWeight.Normal
    ),
    displaySmall = TextStyle(
        fontFamily = qwikFont,
        fontSize = 30.sp,
        fontWeight = FontWeight.Normal
    ),
    headlineLarge = TextStyle(
        fontFamily = qwikFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        fontFamily = qwikFont,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = qwikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = qwikFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        fontFamily = qwikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleSmall = TextStyle(
        fontFamily = qwikFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),
    bodyLarge = TextStyle(
        fontFamily = qwikFont,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontFamily = qwikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontFamily = qwikFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    labelLarge = TextStyle(
        fontFamily = qwikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    labelMedium = TextStyle(
        fontFamily = qwikFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    labelSmall = TextStyle(
        fontFamily = qwikFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
)