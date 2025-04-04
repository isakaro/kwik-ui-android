package com.isakaro.kwik

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.isakaro.kwik.lifecycle.KwikComposableLifeCycle
import com.isakaro.kwik.theme.KwikColorWarning
import com.isakaro.kwik.utils.activity
import com.isakaro.kwik.utils.isPermissionGranted

/**
 * A permission request dialog that can be used to request permissions from the user.
 * @param state: The state of the permisison request. Can be [KwikPermissionRequestState.Requesting], [KwikPermissionRequestState.Granted], [KwikPermissionRequestState.ShowRationale], or [KwikPermissionRequestState.Denied].
 * @param permissions: The list of permissions to request.
 * @param title: The title of the dialog.
 * @param deniedPermanentlyMessage: The message to display when the user denies the permission permanently.
 * @param logo: The logo to display at the top of the dialog.
 * @param image: The image to display in the center of the dialog.
 * @param onGrantAction: The action to perform when the user grants the permission.
 * @param onDeniedAction: The action to perform when the user denies the permission.
 * @param onCancel: The action to perform when the user cancels the dialog.
 * @param mandatory: If true, the dialog will not be cancellable. Defaults to false.
 * @param unSkippable: If true, there won't be a "Later" button. Meaning the user will only have the option to enable the permission. They won't be able to cancel the dialog. Defaults to false.
 * @param cancelText: The text to display on the cancel button. Defaults to "Cancel".
 * @param confirmText: The text to display on the confirm button. Defaults to "Enable".
 * @param laterText: The text to display on the later button. Defaults to "Later".
 *
 * Example usage:
 * ```
 * val state = remember { mutableStateOf(KwikPermissionRequestState.Requesting) }
 *
 * KwikPermissionsRequest(
 *    state = state,
 *    permissions = listOf(KwikPermissionDto(Manifest.permission.READ_EXTERNAL_STORAGE, "Allow app to access your photos and videos to use while creating a listing.")),
 *    title = "Permissions",
 *    deniedPermanentlyMessage = "Permission required. Go to settings to enable",
 *    logo = R.drawable.logo,
 *    icon = R.drawable.shield,
 *    iconTint = Color.Black,
 *    onGrantAction = {
 *      // Handle permission granted
 *    },
 *    onDeniedAction = {
 *      // Handle permission denied
 *    },
 *    onCancel = {
 *      // Handle dialog cancel
 *    },
 *    mandatory = true
 * )
 * ```
 * */
@Composable
fun KwikPermissionsRequest(
    state: MutableState<KwikPermissionRequestState>,
    permissions: List<KwikPermissionDto>,
    title: String,
    deniedPermanentlyMessage: String = "Permission required. Go to settings to enable",
    logo: @Composable () -> Unit = {},
    image: @Composable () -> Unit = {},
    onGrantAction: () -> Unit = {},
    onDeniedAction: () -> Unit = {},
    onCancel: () -> Unit = {},
    mandatory: Boolean = false,
    unSkippable: Boolean = false,
    cancelText: String = "Cancel",
    confirmText: String = "Enable",
    laterText: String = "Later",
) {

    val context = LocalContext.current
    var arePermissionsGranted by remember { mutableStateOf(context.isPermissionGranted(*permissions.map { it.permission }.toTypedArray())) }
    var permissionsExplanationDialogVisible by remember { mutableStateOf(!arePermissionsGranted) }
    val appPackageName = LocalContext.current.packageName
    var permissionRequestState by remember { mutableStateOf(state.value) }

    KwikComposableLifeCycle(
        onResume = {
            arePermissionsGranted = context.isPermissionGranted(*permissions.map { it.permission }.toTypedArray())
            if(arePermissionsGranted){
                permissionsExplanationDialogVisible = false
                onGrantAction()
            } else {
                permissionsExplanationDialogVisible = true
            }
            if(permissionRequestState == KwikPermissionRequestState.ShowRationale){
                permissionRequestState = KwikPermissionRequestState.Requesting
            }
        }
    )

    KwikDialog.ContentDialog(
        open = permissionsExplanationDialogVisible,
        cancellable = false,
        dismiss = {
            permissionsExplanationDialogVisible = false
        }
    ) {
        KwikPermissionRequest(
            permissions = permissions,
            permissionRequestState = state,
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
            logo()

            KwikText.TitleText(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            KwikText.TitleText(
                text = permissions.firstOrNull()?.rationaleMessage ?: "Grant required permissions",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            image()

            if(permissionRequestState == KwikPermissionRequestState.Denied){
                KwikCard(
                    modifier = Modifier.padding(12.dp),
                    containerColor = KwikColorWarning
                ) {
                    KwikText.TitleText(
                        modifier = Modifier.padding(12.dp),
                        text = deniedPermanentlyMessage,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row {
                if(!unSkippable){
                    if(mandatory){
                        KwikTextButton(
                            text = cancelText,
                            onClick = {
                                permissionsExplanationDialogVisible = false
                                onCancel()
                            }
                        )
                    } else {
                        KwikTextButton(
                            text = laterText,
                            onClick = {
                                permissionsExplanationDialogVisible = false
                                onDeniedAction()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                KwikButton(
                    text = confirmText,
                    onClick = {
                        if(permissionRequestState == KwikPermissionRequestState.Denied){
                            context.showInstalledAppDetails(appPackageName)
                        } else {
                            permissionRequestState = KwikPermissionRequestState.Requesting
                        }
                    }
                )
            }
        }
    }
}

fun MutableState<KwikPermissionRequestState>.requestPermissions() {
    this.value = KwikPermissionRequestState.Requesting
}

@Composable
fun rememberKwikPermissionState(): MutableState<KwikPermissionRequestState> {
    return remember { mutableStateOf(KwikPermissionRequestState.Requesting) }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun KwikPermissionRequestPreview() {
    val state = rememberKwikPermissionState()

    KwikPermissionsRequest(
        state = state,
        permissions = listOf(KwikPermissionDto(Manifest.permission.POST_NOTIFICATIONS, "We need the permission to post notifications")),
        title = "Notifications"
    )
}

/**
 * @param @[String] [permission] the permission to be requested
 * @param @[String] [rationaleMessage] the message to show when the user denies the permission but can still show rationale
 * */
data class KwikPermissionDto(
    val permission: String,
    val rationaleMessage: String
)

/**
 * allows to request permissions from the user and handle every possible scenario in one place
 * @param permissions the permissions to be requested and their rationale
 * @param permissionRequestState the current state of the permission request
 * @param onPermissionRequestStateChange callback to handle the state change of the permission request
 * @param onGrantAction optional action that can be performed when the user grants the permission
 * @param onShowRationale optional action that can be performed when the user denies the permission but can still show rationale
 * @param onDeniedAction optional action that can be performed when the user denies the permission and can't show rationale
 * */
@Composable
internal fun KwikPermissionRequest(
    permissions: List<KwikPermissionDto>,
    permissionRequestState: MutableState<KwikPermissionRequestState>,
    onPermissionRequestStateChange: (KwikPermissionRequestState) -> Unit,
    onGrantAction: () -> Unit = {},
    onShowRationale: () -> Unit = {},
    onDeniedAction: () -> Unit = {},
) {
    val permissionList = permissions.map { it.permission }.toTypedArray()
    val context = LocalContext.current.activity()

    // since we have a list of permissions, we grab the first one that was denied but can still show rationale
    fun permissionForRationale(): String? {
        return permissionList.find { shouldShowRequestPermissionRationale(context, it) }
    }

    fun canShowRationale(): Boolean {
        return permissionForRationale() != null
    }

    val getPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grantedPermissionsMap ->
        val newState = when {
            grantedPermissionsMap.all { it.value } -> {
                KwikPermissionRequestState.Granted
            }
            (grantedPermissionsMap.any { !it.value } && canShowRationale()) -> {
                KwikPermissionRequestState.ShowRationale
            }
            else -> {
                KwikPermissionRequestState.Denied
            }
        }
        onPermissionRequestStateChange(newState)
    }

    LaunchedEffect(permissionRequestState.value) {
        when(permissionRequestState.value) {
            KwikPermissionRequestState.Requesting -> {
                getPermission.launch(permissionList)
            }
            KwikPermissionRequestState.Granted -> {
                onGrantAction()
            }
            KwikPermissionRequestState.ShowRationale -> {
                onShowRationale()
            }
            KwikPermissionRequestState.Denied -> {
                onDeniedAction()
            }
        }
    }

}

sealed class KwikPermissionRequestState {
    data object Requesting: KwikPermissionRequestState()
    data object Granted: KwikPermissionRequestState()
    data object ShowRationale: KwikPermissionRequestState()
    data object Denied: KwikPermissionRequestState()
}

internal fun Context.showInstalledAppDetails(appPackageName: String) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", appPackageName, null)
    intent.data = uri
    startActivity(intent)
}