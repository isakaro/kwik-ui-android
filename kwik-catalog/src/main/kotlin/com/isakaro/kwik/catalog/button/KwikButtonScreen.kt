package com.isakaro.kwik.catalog.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.button.KwikButtonLoadingStyle
import com.isakaro.kwik.ui.button.KwikExtendedFloatingActionButton
import com.isakaro.kwik.ui.button.KwikFloatingActionButton
import com.isakaro.kwik.ui.button.KwikTextButton
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikButtonScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ScrollableShowCaseContainer(
        title = "Accordion",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        NormalButton()
        ButtonWithLeadingIcon()
        ButtonWithTrailingIcon()
        NormalMaxWidthButton()
        OutlinedButton()
        NormalMaxWidthOutlinedButton()
        DisabledButton()
        ButtonWithCustomShape()
        TextButton()
        TextButtonWithCustomContent()
        LoadingButtonLinear()
        LoadingButtonCircular()
        FabButton()
        DisabledFabButton()
        LoadingExtendedFloatingActionButton()
        ExtendedButton()
        DisabledExtendedButton()
        ButtonWithIcon()
    }
}

@Composable
private fun NormalButton() {
    ShowCase("Button") {
        KwikButton(
            text = "Action",
            onClick = {}
        )
    }
}

@Composable
private fun ButtonWithLeadingIcon() {
    ShowCase("Button with leading icon") {
        KwikTheme {
            KwikButton(
                text = "Action",
                leadingIcon = Icons.Default.Settings,
                onClick = {  }
            )
        }
    }
}

@Composable
private fun ButtonWithTrailingIcon() {
    ShowCase("Button with trailing icon") {
        KwikTheme {
            KwikButton(
                text = "Action",
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = {  }
            )
        }
    }
}

@Composable
private fun NormalMaxWidthButton() {
    ShowCase("Max width button") {
        KwikButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = "Action"
        )
    }
}

@Composable
private fun OutlinedButton() {
    ShowCase("Outlined Button") {
        KwikButton(
            text = "Action",
            outlined = true,
            onClick = {}
        )
    }
}

@Composable
private fun NormalMaxWidthOutlinedButton() {
    ShowCase("Max width outlined button") {
        KwikButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Action",
            outlined = true,
            onClick = {}
        )
    }
}

@Composable
private fun ButtonWithCustomShape() {
    ShowCase("Button with custom shape") {
        KwikButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Action",
            shape = RoundedCornerShape(24.dp),
            onClick = {}
        )
    }
}

@Composable
private fun ButtonWithIcon() {
    ShowCase("Icon Button") {
        KwikButton(
            text = "Action",
            leadingIcon = R.drawable.qr_code_scanner,
            onClick = {}
        )
    }
}

@Composable
private fun DisabledButton() {
    ShowCase("Disabled Button") {
        KwikButton(
            text = "Action",
            onClick = {},
            enabled = false
        )
    }
}

@Composable
private fun LoadingButtonLinear() {
    ShowCase("Linear loading style Button") {
        KwikButton(
            text = "Action",
            onClick = {},
            isLoading = true,
            kwikButtonLoadingStyle = KwikButtonLoadingStyle.LINEAR,
            loadingText = "Loading. Please wait..."
        )
    }
}

@Composable
private fun LoadingButtonCircular() {
    ShowCase("Circular loading style Button") {
        KwikButton(
            text = "Action",
            onClick = {},
            isLoading = true,
            loadingText = "Loading. Please wait..."
        )
    }
}

@Composable
private fun TextButton() {
    ShowCase("Text Button") {
        KwikTextButton(
            text = "Action",
            onClick = {}
        )
    }
}

@Composable
private fun TextButtonWithCustomContent() {
    ShowCase("Text Button with custom content") {
        KwikTextButton(
            text = {
                KwikText.RenderText(
                    text = "Action",
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            },
            onClick = {}
        )
    }
}

@Composable
private fun FabButton() {
    ShowCase("Floating Action button") {
        KwikFloatingActionButton(onClick = {}, contentColor = Color.White) {
            KwikText.TitleSmall(
                text = "Action",
                color = Color.White
            )
        }
    }
}

@Composable
private fun DisabledFabButton() {
    ShowCase("Disabled Floating Action button") {
        KwikFloatingActionButton(
            contentColor = Color.White,
            enabled = false,
            onClick = {}
        ) {
            KwikText.TitleSmall(
                text = "Action",
                color = Color.White
            )
        }
    }
}

@Composable
private fun ExtendedButton() {
    ShowCase("Extended Floating Action") {
        KwikExtendedFloatingActionButton(
            text = { KwikText.TitleSmall(text = "Action", color = Color.White) },
            icon = { Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share") },
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
    }
}

@Composable
private fun LoadingExtendedFloatingActionButton() {
    ShowCase("Loading Extended Floating Action Button") {
        KwikExtendedFloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            text = {
                KwikText.TitleSmall(text = "Action")
            },
            icon = {
                Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share")
            },
            loading = true,
            loadingText = "Loading...",
            onClick = { }
        )
    }
}

@Composable
private fun DisabledExtendedButton() {
    ShowCase("Disabled Extended Floating Action") {
        KwikExtendedFloatingActionButton(
            text = { KwikText.TitleSmall(text = "Action", color = Color.White) },
            icon = { Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share") },
            enabled = false,
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikButtonScreen() {
    KwikTheme {
        KwikButtonScreen()
    }
}
