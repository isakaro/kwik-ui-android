package com.isakaro.kwik.catalog.typography

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.text.KwikExpandableText
import com.isakaro.kwik.ui.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
fun KwikTypographyScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ScrollableShowCaseContainer(
        title = "Typography",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Expandable Text") {
            KwikExpandableText(
                text = "This is the day you will always remember as the day you almost caught Captain Jack Sparrow!. This is the day you will always remember as the day you almost caught Captain Jack Sparrow!. This is the day you will always remember as the day you almost caught Captain Jack Sparrow!. This is the day you will always remember as the day you almost caught Captain Jack Sparrow!. This is the day you will always remember as the day you almost caught Captain Jack Sparrow!. This is the day you will always remember as the day you almost caught Captain Jack Sparrow!. This is the day you will always remember as the day you almost caught Captain Jack Sparrow!.",
                readMoreText = "Read more",
                showLessText = "Show less",
                maxLines = 3
            )
        }

        ShowCase(title = "Quote text") {
            KwikText.Quote(
                text = "This is the day you will always remember as the day you almost caught Captain Jack Sparrow!",
                author = "Jack Sparrow"
            )
        }

        ShowCase("Typography") {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                KwikText.DisplayLarge(text = "DisplayLarge")

                KwikText.DisplayMedium(text = "DisplayMedium")

                KwikText.DisplaySmall(text = "DisplaySmall")

                KwikText.HeadlineLarge(text = "HeadlineLarge")

                KwikText.HeadlineMedium(text = "HeadlineMedium")

                KwikText.HeadlineSmall(text = "HeadlineSmall")

                KwikText.TitleLarge(text = "TitleLarge")

                KwikText.TitleMedium(text = "TitleMedium")

                KwikText.TitleSmall(text = "TitleSmall")

                KwikText.BodyLarge(text = "BodyLarge")

                KwikText.BodyMedium(text = "BodyMedium")

                KwikText.BodySmall(text = "BodySmall")

                KwikText.LabelLarge(text = "LabelLarge")

                KwikText.LabelMedium(text = "LabelMedium")

                KwikText.LabelSmall(text = "LabelSmall")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikTypographyScreen() {
    KwikTheme {
        KwikTypographyScreen()
    }
}
