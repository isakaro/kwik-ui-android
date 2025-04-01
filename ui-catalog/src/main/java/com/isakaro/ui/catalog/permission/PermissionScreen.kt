package com.isakaro.ui.catalog.permission

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.ui.catalog.common.ShowCaseContainer
import app.isakaro.ui.library.theme.Theme.AmpersandTheme

@Composable
internal fun PermissionsScreen() {
    ShowCaseContainer {
        PermissionRequest(
            permissions = listOf(
                PermissionRequest.Permission(
                    Manifest.permission.CAMERA,
                    "Camera permissions is needed to scan QR codes and take photos"
                ),
                PermissionRequest.Permission(
                    Manifest.permission.BLUETOOTH,
                    "Bluetooth permissions is needed"
                ),
                PermissionRequest.Permission(Manifest.permission.ACCESS_FINE_LOCATION, "Location permissions is required to show you nearby stations"),
                PermissionRequest.Permission(Manifest.permission.ACCESS_COARSE_LOCATION, "Location permissions is required to show you nearby stations")
            ),
            deniedPermanentlyMessage = "Permissions were denied. Go to settings to enable",
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = app.isakaro.ui.library.R.drawable.shield),
                    contentDescription = "Permission granted",
                    tint = Color.Black
                )
                Text(
                    text = "Permission granted",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    AmpersandTheme {
        PermissionsScreen()
    }
}
