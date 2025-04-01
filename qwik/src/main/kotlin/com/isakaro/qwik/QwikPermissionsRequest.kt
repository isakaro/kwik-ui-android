package com.isakaro.qwik

import android.Manifest
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.lifecycle.QwikComposableLifeCycle
import com.isakaro.qwik.theme.QwikColorOrange

@Composable
fun QwikPermissionsRequest(
    permissions: List<QwikPermissionDto>,
    title: String,
    deniedPermanentlyMessage: String = "Permission required. Go to settings to enable",
    @DrawableRes logo: Int? = null,
    @DrawableRes icon: Int = R.drawable.shield,
    iconTint: Color? = null,
    onGrantAction: () -> Unit = {},
    onDeniedAction: () -> Unit = {},
    onCancel: () -> Unit = {},
    mandatory: Boolean = true
) {

    val context = LocalContext.current
    var arePermissionsGranted by remember { mutableStateOf(context.isPermissionGranted(*permissions.map { it.permission }.toTypedArray())) }
    var permissionsExplanationDialogVisible by remember { mutableStateOf(!arePermissionsGranted) }
    val appPackageName = LocalContext.current.packageName
    var permissionRequestState by remember { mutableStateOf<QwikPermissionRequestState>(
        QwikPermissionRequestState.Requesting) }

    QwikComposableLifeCycle(
        onResume = {
            arePermissionsGranted = context.isPermissionGranted(*permissions.map { it.permission }.toTypedArray())
            if(arePermissionsGranted){
                permissionsExplanationDialogVisible = false
                onGrantAction()
            } else {
                permissionsExplanationDialogVisible = true
            }
            if(permissionRequestState == QwikPermissionRequestState.ShowRationale){
                permissionRequestState = QwikPermissionRequestState.Requesting
            }
        }
    )

    QwikDialog.ContentDialog(
        open = permissionsExplanationDialogVisible,
        cancellable = false,
        dismiss = {
            permissionsExplanationDialogVisible = false
        }
    ) {
        QwikPermissionRequest(
            permissions = permissions,
            permissionRequestState = permissionRequestState,
            onPermissionRequestStateChange = { newState ->
                permissionRequestState = newState
            },
            onGrantAction = {
                permissionsExplanationDialogVisible = false
                onGrantAction()
            },
            onDeniedAction = {
                onDeniedAction()
            }
        )

        Column(
            modifier = Modifier.padding(12.dp).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            if(logo != null){
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 15.dp),
                    painter = painterResource(id = logo),
                    contentDescription = "Permission security icon"
                )
            }

            IsakaroText.TitleText(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            IsakaroText.TitleText(
                text = permissions.firstOrNull()?.rationaleMessage ?: "Grant required permissions",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            if(iconTint != null){
                Icon(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(bottom = 15.dp),
                    painter = painterResource(icon),
                    contentDescription = "enable permissions",
                    tint = iconTint
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(bottom = 15.dp),
                    painter = painterResource(id = icon),
                    contentDescription = "enable permissions"
                )
            }

            if(permissionRequestState == QwikPermissionRequestState.Denied){
                QwikCard(
                    modifier = Modifier.padding(12.dp),
                    containerColor = QwikColorOrange
                ) {
                    IsakaroText.TitleText(
                        modifier = Modifier.padding(12.dp),
                        text = deniedPermanentlyMessage,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row {
                if(mandatory){
                    QwikTextButton(
                        text = "Cancel",
                        onClick = {
                            permissionsExplanationDialogVisible = false
                            onCancel()
                        }
                    )
                } else {
                    QwikTextButton(
                        text = "Later",
                        onClick = {
                            permissionsExplanationDialogVisible = false
                            onDeniedAction()
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                QwikSimpleButton(
                    text = "Enable",
                    onClick = {
                        if(permissionRequestState == QwikPermissionRequestState.Denied){
                            context.showInstalledAppDetails(appPackageName)
                        } else {
                            permissionRequestState = QwikPermissionRequestState.Requesting
                        }
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun QwikPermissionRequestPreview() {
    QwikPermissionsRequest(
        permissions = listOf(QwikPermissionDto(Manifest.permission.POST_NOTIFICATIONS, "We need the permission to post notifications")),
        title = "Notifications"
    )
}