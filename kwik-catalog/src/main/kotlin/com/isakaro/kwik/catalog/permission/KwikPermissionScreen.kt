package com.isakaro.kwik.catalog.permission

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.checkbox.KwikCheckBox
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.permissions.KwikPermissionDto
import com.isakaro.kwik.ui.permissions.KwikPermissionsRequest
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.toast.KwikToast
import com.isakaro.kwik.ui.toast.KwikToastType
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.permissions.rememberKwikPermissionState
import com.isakaro.kwik.ui.toast.rememberKwikToastState
import com.isakaro.kwik.ui.permissions.requestPermissions
import com.isakaro.kwik.ui.toast.showToast
import com.isakaro.kwik.theme.KwikTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(style = SlideInFromRightAnimations::class)
internal fun KwikPermissionsScreen(
    navigator: DestinationsNavigator = navigator()
) {

    val kwikToastState = rememberKwikToastState()
    val permissionState = rememberKwikPermissionState()
    var granted by remember { mutableStateOf(false) }
    var mandatory by remember { mutableStateOf(false) }

    KwikToast(state = kwikToastState)

    ShowCaseContainer(
        title = "Permission Screen",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        KwikPermissionsRequest(
            state = permissionState.value,
            mandatory = mandatory,
            permissions = when {
                Build.VERSION.SDK_INT >= 33 -> {
                    listOf(
                        KwikPermissionDto(Manifest.permission.POST_NOTIFICATIONS, "Allow app to post notifications."),
                    )
                }
                else -> {
                    listOf()
                }
            },
            title = "Permissions required",
            logo = {
                KwikImageView(
                    modifier = Modifier.size(50.dp),
                    url = R.drawable.kwikui_logo
                )
            },
            image = {
                KwikImageView(
                    modifier = Modifier.size(120.dp),
                    url = Icons.Default.Build,
                    tint = Color.Gray
                )
            },
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

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically)
        ) {
            if(granted){
                // perform action when permission is granted
                KwikImageView(
                    modifier = Modifier.size(120.dp),
                    url = R.drawable.kwikui_logo
                )
                KwikText.TitleMedium(
                    text = "Permission granted"
                )
            } else {
                /*
                * perform action when permission is not granted
                * */
                KwikImageView(
                    modifier = Modifier.size(120.dp),
                    url = R.drawable.kwikui_logo
                )
                KwikText.BodyLarge(
                    text = "Permission not granted"
                )
                KwikButton(
                    text = "Request permission",
                    onClick = {
                        permissionState.requestPermissions()
                    }
                )
            }

            KwikCheckBox(
                text = "Mandatory",
                checked = mandatory,
                onCheckedChange = {
                    mandatory = it
                }
            )
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
