package com.isakaro.kwik.ui.rating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.isakaro.R
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.theme.KwikColorError
import com.isakaro.kwik.ui.theme.KwikColorSuccess
import com.isakaro.kwik.ui.theme.KwikColorYellow
import kotlin.math.floor

/**
 * A rating bar that displays and optionally allows setting a rating using stars.
 *
 * @param modifier The modifier to apply to this layout.
 * @param rating The initial rating to display (0.0 when clickable mode is enabled).
 * @param stars The total number of stars to display.
 * @param starsColor The color of the filled stars.
 * @param starSize The size of each star icon.
 * @param spacing The spacing between stars.
 * @param showBadge Whether to show a badge displaying the numerical rating.
 * @param badgeContainerColor The background color of the badge, determined by rating by default.
 * @param badgeContentColor The text color of the badge.
 * @param clickable Whether stars can be clicked to set a rating.
 * @param onClick Callback function that receives the selected rating (1-5) when a star is clicked.
 *
 * Example usage:
 *
 * Display-only mode:
 * ```
 * KwikRatingBar(
 *     stars = 5,
 *     rating = 4.5,
 *     showBadge = true
 * )
 * ```
 *
 * Clickable mode:
 * ```
 * var userRating by remember { mutableStateOf(0) }
 * KwikRatingBar(
 *     stars = 5,
 *     rating = userRating.toDouble(),
 *     clickable = true,
 *     onClick = { selectedRating ->
 *         userRating = selectedRating
 *     }
 * )
 * ```
 */
@Composable
fun KwikRatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = KwikColorYellow,
    starSize: Dp = 24.dp,
    spacing: Dp = 2.dp,
    showBadge: Boolean = true,
    badgeContainerColor: Color = determineRatingColor(rating),
    badgeContentColor: Color = Color.DarkGray,
    clickable: Boolean = false,
    onClick: (Int) -> Unit = {}
) {
    val displayRating = if (clickable && rating == 0.0) 0.0 else rating.coerceIn(0.0, stars.toDouble())
    val filledStars = floor(displayRating).toInt()
    val halfStar = !(displayRating.rem(1).equals(0.0))

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 1..stars) {
            val starModifier = Modifier
                .size(starSize)
                .then(
                    if (clickable) {
                        Modifier.clickable { onClick(index) }
                    } else {
                        Modifier
                    }
                )

            when {
                index <= filledStars -> {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Rating $index of $stars",
                        modifier = starModifier,
                        tint = starsColor
                    )
                }
                index == filledStars + 1 && halfStar -> {
                    Icon(
                        painter = painterResource(R.drawable.star_half),
                        contentDescription = "Rating $index of $stars (half)",
                        modifier = starModifier,
                        tint = starsColor
                    )
                }
                else -> {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Rating $index of $stars (empty)",
                        modifier = starModifier,
                        tint = Color.LightGray
                    )
                }
            }

            if (index < stars) {
                Spacer(modifier = Modifier.width(spacing))
            }
        }

        if (showBadge) {
            Spacer(modifier = Modifier.width(spacing))
            Badge(
                containerColor = badgeContainerColor,
            ) {
                KwikText.TitleSmall(
                    text = displayRating.toString(),
                    color = badgeContentColor
                )
            }
        }
    }
}

/**
 * Determines the appropriate color based on the rating value.
 *
 * @param rating The rating value to evaluate.
 * @return A color representing the rating quality (red for low, yellow for medium, green for high).
 */
private fun determineRatingColor(rating: Double): Color {
    return when(rating) {
        in 0.0..1.9 -> KwikColorError
        in 2.0..2.9 -> KwikColorYellow
        in 3.0..5.0 -> KwikColorSuccess
        else -> KwikColorSuccess
    }
}

@Preview
@Composable
private fun FiveStarsRatingPreview() {
    KwikRatingBar(
        stars = 5,
        rating = 5.0
    )
}

@Preview
@Composable
private fun TwoPoint5iveRatingPreview() {
    KwikRatingBar(
        rating = 2.5
    )
}
