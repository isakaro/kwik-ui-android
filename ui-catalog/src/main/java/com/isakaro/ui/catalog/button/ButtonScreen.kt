package com.isakaro.ui.catalog.button

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
import app.isakaro.appcatalog.R
import com.isakaro.ui.catalog.common.ScrollableShowCaseContainer
import com.isakaro.ui.catalog.common.ShowCase
import app.isakaro.ui.library.theme.PrimaryColor
import app.isakaro.ui.library.theme.Theme.AmpersandTheme

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
        AmpersandButton(onClick = {}, text = "action", buttonStyle = AmpersandButton.ButtonStyle.NORMAL)
    }
}

@Composable
private fun OutlinedButton() {
    ShowCase("Outlined Button") {
        AmpersandButton(onClick = {}, text = "action", buttonStyle = AmpersandButton.ButtonStyle.OUTLINED)
    }
}

@Composable
private fun ButtonWithIcon() {
    ShowCase("Icon Button") {
        AmpersandButton(onClick = {}, text = "action", icon = R.drawable.qr_code_scanner)
    }
}

@Composable
private fun DisabledButton() {
    ShowCase("Disabled Button") {
        AmpersandButton(
            onClick = {},
            text = "action",
            buttonStyle = AmpersandButton.ButtonStyle.NORMAL,
            disabled = true
        )
    }
}

@Composable
private fun LoadingButton() {
    ShowCase("Loading Button") {
        AmpersandButton(
            onClick = {},
            text = "action",
            loadingState = AmpersandButton.LoadingState(true, "loading..."),
        )
    }
}

@Composable
private fun TextButton() {
    ShowCase("Text Button") {
        AmpersandButton(onClick = {}, text = "action", buttonStyle = AmpersandButton.ButtonStyle.TEXT)
    }
}

@Composable
private fun FabButton() {
    ShowCase("Icon Floating Action") {
        FloatingActionButton(onClick = {}, containerColor = PrimaryColor, contentColor = Color.Black) {
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
            containerColor = PrimaryColor,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        ButtonScreen()
    }
}
