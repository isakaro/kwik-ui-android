package com.isakaro.kwik.catalog.avatar

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.catalog.utils.KwikConstants
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.avatar.KwikAvatar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikAvatarScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Avatar",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Avatar") {
            KwikAvatar(
                modifier = Modifier.size(300.dp),
                url = KwikConstants.SAMPLE_IMAGE
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikAvatarScreen() {
    KwikAvatarScreen()
}
