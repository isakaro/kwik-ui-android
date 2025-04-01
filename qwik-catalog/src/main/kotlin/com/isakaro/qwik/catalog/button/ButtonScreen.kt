package com.isakaro.qwik.catalog.button

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.qwik.QwikButton
import com.isakaro.qwik.R
import com.isakaro.qwik.catalog.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.ShowCase
import com.isakaro.qwik.theme.Theme.QwikTheme

@Composable
internal fun ButtonScreen() {
    ScrollableShowCaseContainer {
        NormalButton()
        OutlinedButton()
        TextButton()
        DisabledButton()
        FabButton()
        ExtendedButton()
        ButtonWithIcon()
        LoadingButton()
    }
}

@Composable
private fun NormalButton() {
    ShowCase("Button") {
        QwikButton(onClick = {}, text = "action")
    }
}

@Composable
private fun OutlinedButton() {
    ShowCase("Outlined Button") {
        QwikButton(onClick = {}, text = "action", outlined = true)
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
private fun LoadingButton() {
    ShowCase("Loading Button") {
        QwikButton(
            onClick = {},
            text = "action",
            isLoading = true,
            loadingText = "In Progress"
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
            Icon(Icons.Sharp.Share, "share")
        }
    }
}

@Composable
private fun ExtendedButton() {
    ShowCase("Extended Floating Action") {
        ExtendedFloatingActionButton(
            text = { Text(text = "Action", color = Color.Black, style = MaterialTheme.typography.titleSmall) },
            icon = { Icon(Icons.Sharp.Share, tint = Color.Black, contentDescription = "share") },
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    QwikTheme {
        ButtonScreen()
    }
}
