package com.isakaro.kwik.catalog.permission

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.KwikButton
import com.isakaro.kwik.KwikPermissionDto
import com.isakaro.kwik.KwikPermissionsRequest
import com.isakaro.kwik.KwikToast
import com.isakaro.kwik.KwikToastType
import com.isakaro.kwik.R
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.rememberKwikPermissionState
import com.isakaro.kwik.rememberKwikToastState
import com.isakaro.kwik.requestPermissions
import com.isakaro.kwik.showToast
import com.isakaro.kwik.theme.Theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
internal fun KwikPermissionsScreen(
    navigator: DestinationsNavigator = navigator()
) {

    val kwikToastState = rememberKwikToastState()
    val permissionState = rememberKwikPermissionState()
    var granted by remember { mutableStateOf(false) }

    if (kwikToastState.value.isVisible) {
        KwikToast(state = kwikToastState)
    }

    ShowCaseContainer(
        title = "Permission Screen",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        KwikPermissionsRequest(
            state = permissionState,
            mandatory = true,
            permissions = when {
                Build.VERSION.SDK_INT >= 33 -> {
                    listOf(
                        KwikPermissionDto(Manifest.permission.READ_MEDIA_IMAGES, "Allow app to access your photos and videos to use while creating a listing."),
                        KwikPermissionDto(Manifest.permission.READ_MEDIA_VIDEO, "Allow app to access your photos and videos to use while creating a listing.")
                    )
                }
                else -> {
                    listOf(
                        KwikPermissionDto(Manifest.permission.READ_EXTERNAL_STORAGE, "Allow app to access your photos and videos to use while creating a listing.")
                    )
                }
            },
            title = "Media access required",
            logo = com.isakaro.Kwik.catalog.R.mipmap.ic_launcher,
            icon = R.drawable.shield,
            iconTint = Color.Black,
            onGrantAction = {
                granted = true
            },
            onDeniedAction = {
                kwikToastState.showToast(message = "These permissions are necessary for the feature to function", type = KwikToastType.WARNING)
            },
            onCancel = {
                kwikToastState.showToast(message = "These permissions are necessary for the feature to function", type = KwikToastType.WARNING)
            }
        )

        if(granted){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.shield),
                    contentDescription = "Permission granted",
                    tint = Color.Black
                )
                Text(
                    text = "Permission granted",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.shield),
                    contentDescription = "Permission not granted",
                    tint = Color.Black
                )
                Text(
                    text = "Permission not granted",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                KwikButton(
                    text = "Request permission",
                    onClick = {
                        permissionState.requestPermissions()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewKwikPermissionsScreen() {
    KwikTheme {
        KwikPermissionsScreen()
    }
}
