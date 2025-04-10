package com.isakaro.kwik.catalog.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.KwikButton
import com.isakaro.kwik.KwikButtonLoadingStyle
import com.isakaro.kwik.KwikExtendedFloatingActionButton
import com.isakaro.kwik.KwikFloatingActionButton
import com.isakaro.kwik.R
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikTheme
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
        NormalMaxWidthButton()
        OutlinedButton()
        NormalMaxWidthOutlinedButton()
        TextButton()
        DisabledButton()
        LoadingButtonLinear()
        LoadingButtonCircular()
        FabButton()
        LoadingExtendedFloatingActionButton()
        ExtendedButton()
        LoadingFloatingActionButton()
        DisabledExtendedButton()
        ButtonWithIcon()
    }
}

@Composable
private fun NormalButton() {
    ShowCase("Button") {
        KwikButton(onClick = {}, text = "action")
    }
}

@Composable
private fun NormalMaxWidthButton() {
    ShowCase("Max width button") {
        KwikButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = "action"
        )
    }
}

@Composable
private fun OutlinedButton() {
    ShowCase("Outlined Button") {
        KwikButton(onClick = {}, text = "action", outlined = true)
    }
}

@Composable
private fun NormalMaxWidthOutlinedButton() {
    ShowCase("Max width outlined button") {
        KwikButton(
            modifier = Modifier.fillMaxWidth(),
            outlined = true,
            onClick = {},
            text = "action"
        )
    }
}

@Composable
private fun ButtonWithIcon() {
    ShowCase("Icon Button") {
        KwikButton(onClick = {}, text = "action", leadingIcon = R.drawable.shield)
    }
}

@Composable
private fun DisabledButton() {
    ShowCase("Disabled Button") {
        KwikButton(
            onClick = {},
            text = "action",
            enabled = false
        )
    }
}

@Composable
private fun LoadingButtonLinear() {
    ShowCase("Linear loading style Button") {
        KwikButton(
            onClick = {},
            text = "action",
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
            onClick = {},
            text = "action",
            isLoading = true,
            loadingText = "Loading. Please wait..."
        )
    }
}

@Composable
private fun TextButton() {
    ShowCase("Text Button") {
        KwikButton(onClick = {}, text = "action")
    }
}

@Composable
private fun FabButton() {
    ShowCase("Icon Floating Action") {
        FloatingActionButton(onClick = {}, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.Black) {
            Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share")
        }
    }
}

@Composable
private fun ExtendedButton() {
    ShowCase("Extended Floating Action") {
        KwikExtendedFloatingActionButton(
            text = { Text(text = "Action", style = MaterialTheme.typography.titleSmall) },
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
                Text(text = "Action", style = MaterialTheme.typography.titleSmall)
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
            text = { Text(text = "Action", style = MaterialTheme.typography.titleSmall) },
            icon = { Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share") },
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
    }
}

@Composable
private fun LoadingFloatingActionButton() {
    ShowCase("Loading Floating Action Button") {
        KwikFloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            loading = true,
            onClick = { }
        ){
            Row {
                Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share")
                Text(text = "Action", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikButtonScreen() {
    KwikTheme {
        KwikButtonScreen()
    }
}
