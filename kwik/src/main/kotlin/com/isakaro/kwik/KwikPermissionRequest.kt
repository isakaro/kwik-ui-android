package com.isakaro.Kwik

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.isakaro.Kwik.utils.activity

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