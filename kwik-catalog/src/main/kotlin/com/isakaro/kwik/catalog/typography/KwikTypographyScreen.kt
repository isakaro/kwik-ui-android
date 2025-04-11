package com.isakaro.kwik.catalog.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikText
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun KwikTypographyScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ScrollableShowCaseContainer(
        title = "Typography",
        onBackClick = {
            navigator.navigateUp()
        }
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

        KwikText.LabelLarge(text = "LabelLarge")

        KwikText.LabelMedium(text = "LabelMedium")

        KwikText.LabelSmall(text = "LabelSmall")

        KwikText.BodyLarge(text = "BodyLarge")

        KwikText.BodyMedium(text = "BodyMedium")

        KwikText.BodySmall(text = "BodySmall")
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikTypographyScreen() {
    KwikTheme {
        KwikTypographyScreen()
    }
}
