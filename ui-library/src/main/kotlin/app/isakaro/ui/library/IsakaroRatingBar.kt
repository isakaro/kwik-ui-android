package app.isakaro.ui.library

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.ui.library.R
import com.isakaro.ui.components.IsakaroText
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun IsakaroRatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor)
        }
        if (halfStar) {
            Icon(
                painter = painterResource(R.drawable.star_half),
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        Badge(
            containerColor = determineRatingColor(rating),
        ) {
            IsakaroText.TitleText(
                text = rating.toString(),
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

private fun determineRatingColor(rating: Double): Color {
    return when(rating) {
        in 0.0..1.9 -> Color.Red
        in 2.0..2.9 -> Color.Yellow
        in 3.0..5.0 -> Color.Green
        else -> Color.Green
    }
}

@Preview
@Composable
fun FiveStarsRatingPreview() {
    IsakaroRatingBar(stars = 5, rating = 5.0)
}

@Preview
@Composable
fun TwoPoint5iveRatingPreview() {
    IsakaroRatingBar(rating = 2.5)
}
