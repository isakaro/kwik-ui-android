package com.isakaro.kwik.catalog.shape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.helpers.KwikCenterColumn
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
fun KwikShapeScreen(
    navigator: DestinationsNavigator = navigator()
) {

    ScrollableShowCaseContainer(
        title = "Shapes",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShapeShowCase(
            title = "extra small",
            shape = MaterialTheme.shapes.extraSmall
        )

        ShapeShowCase(
            title = "small",
            shape = MaterialTheme.shapes.small
        )

        ShapeShowCase(
            title = "medium",
            shape = MaterialTheme.shapes.medium
        )

        ShapeShowCase(
            title = "large",
            shape = MaterialTheme.shapes.large
        )

        ShapeShowCase(
            title = "extra large",
            shape = MaterialTheme.shapes.extraLarge
        )
    }

}

@Composable
private fun ShapeShowCase(
    title: String,
    shape: Shape
) {
    KwikCenterColumn {
        KwikText.BodyLarge(
            text = title
        )

        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary, shape = shape)
                .fillMaxWidth()
                .height(30.dp)
        )
    }

    KwikVSpacer(12)
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikShapeScreen() {
    KwikTheme {
        KwikShapeScreen()
    }
}
