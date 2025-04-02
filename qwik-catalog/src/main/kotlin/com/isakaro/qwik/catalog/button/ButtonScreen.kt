package com.isakaro.qwik.catalog.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikButton
import com.isakaro.qwik.QwikButtonLoadingStyle
import com.isakaro.qwik.R
import com.isakaro.qwik.catalog.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.theme.Theme.QwikTheme

@Composable
internal fun QwikButtonScreen() {
    ScrollableShowCaseContainer {
        NormalButton()
        NormalMaxWidthButton()
        OutlinedButton()
        NormalMaxWidthOutlinedButton()
        TextButton()
        DisabledButton()
        FabButton()
        ExtendedButton()
        ButtonWithIcon()
        LoadingButtonLinear()
        LoadingButtonCircular()
    }
}

@Composable
private fun NormalButton() {
    ShowCase("Button") {
        QwikButton(onClick = {}, text = "action")
    }
}

@Composable
private fun NormalMaxWidthButton() {
    ShowCase("Max width button") {
        QwikButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = "action"
        )
    }
}

@Composable
private fun OutlinedButton() {
    ShowCase("Outlined Button") {
        QwikButton(onClick = {}, text = "action", outlined = true)
    }
}

@Composable
private fun NormalMaxWidthOutlinedButton() {
    ShowCase("Max width outlined button") {
        QwikButton(
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
        QwikButton(onClick = {}, text = "action", leadingIcon = R.drawable.shield)
    }
}

@Composable
private fun DisabledButton() {
    ShowCase("Disabled Button") {
        QwikButton(
            onClick = {},
            text = "action",
            enabled = false
        )
    }
}

@Composable
private fun LoadingButtonLinear() {
    ShowCase("Linear loading style Button") {
        QwikButton(
            onClick = {},
            text = "action",
            isLoading = true,
            qwikButtonLoadingStyle = QwikButtonLoadingStyle.LINEAR,
            loadingText = "Loading. Please wait..."
        )
    }
}

@Composable
private fun LoadingButtonCircular() {
    ShowCase("Circular loading style Button") {
        QwikButton(
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
        QwikButton(onClick = {}, text = "action")
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
        ExtendedFloatingActionButton(
            text = { Text(text = "Action", style = MaterialTheme.typography.titleSmall) },
            icon = { Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share") },
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQwikButtonScreen() {
    QwikTheme {
        QwikButtonScreen()
    }
}
