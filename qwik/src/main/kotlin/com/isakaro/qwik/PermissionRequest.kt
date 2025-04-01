package com.isakaro.qwik

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.isakaro.qwik.utils.activity

/**
 * @param @[String] [permission] the permission to be requested
 * @param @[String] [rationaleMessage] the message to show when the user denies the permission but can still show rationale
 * */
data class PermissionDto(
    val permission: String,
    val rationaleMessage: String
)

/**
 * allows to request permissions from the user and handle every possible scenario in one place
 * @param @[List] [permissions] the permissions to be requested and their rationale
 * @param @[String] [appPackageName] action to launch intent to the settings screen. Must be handled in the calling screen
 * @param @[Unit] [onGrantAction] optional action that can be performed when the user grants the permission
 * @param @[Composable] [content] optional content that can be displayed when the user grants the permission
 * */
@Composable
fun PermissionRequest(
    permissions: List<PermissionDto>,
    permissionRequestState: PermissionRequestState,
    onPermissionRequestStateChange: (PermissionRequestState) -> Unit,
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
                PermissionRequestState.Granted
            }
            (grantedPermissionsMap.any { !it.value } && canShowRationale()) -> {
                PermissionRequestState.ShowRationale
            }
            else -> {
                PermissionRequestState.Denied
            }
        }
        onPermissionRequestStateChange(newState)
    }

   LaunchedEffect(permissionRequestState) {
       when(permissionRequestState) {
           PermissionRequestState.Requesting -> {
               getPermission.launch(permissionList)
           }
           PermissionRequestState.Granted -> {
               onGrantAction()
           }
           PermissionRequestState.ShowRationale -> {
               onShowRationale()
           }
           PermissionRequestState.Denied -> {
               onDeniedAction()
           }
       }
   }

}

sealed class PermissionRequestState {
    data object Requesting: PermissionRequestState()
    data object Granted: PermissionRequestState()
    data object ShowRationale: PermissionRequestState()
    data object Denied: PermissionRequestState()
}

fun Context.showInstalledAppDetails(appPackageName: String) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", appPackageName, null)
    intent.data = uri
    startActivity(intent)
}