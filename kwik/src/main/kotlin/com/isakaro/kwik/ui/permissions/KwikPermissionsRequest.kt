package com.isakaro.kwik.ui.permissions

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
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.button.KwikTextButton
import com.isakaro.kwik.ui.card.KwikCard
import com.isakaro.kwik.ui.dialog.KwikDialog
import com.isakaro.kwik.ui.lifecycle.KwikComposableLifeCycle
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.theme.KwikColorWarning
import com.isakaro.kwik.ui.utils.activity
import com.isakaro.kwik.ui.utils.isPermissionGranted
import java.util.UUID

/**
 * A permission request dialog that can be used to request permissions from the user.
 * @param state: The state of the permission request. Can be [KwikPermissionRequestState.Requesting], [KwikPermissionRequestState.Granted], [KwikPermissionRequestState.ShowRationale], or [KwikPermissionRequestState.Denied].
 * @param permissions: The list of permissions to request.
 * @param title: The title of the dialog.
 * @param deniedPermanentlyMessage: The message to display when the user denies the permission permanently.
 * @param logo: The logo to display at the top of the dialog.
 * @param image: The image to display in the center of the dialog.
 * @param onGrantAction: The action to perform when the user grants the permission.
 * @param onDeniedAction: The action to perform when the user denies the permission.
 * @param onCancel: The action to perform when the user cancels the dialog. This isn't available when [mandatory] is true.
 * @param mandatory: If true, there won't be a "Cancel" button. They won't be able to cancel the dialog. Additionally, the user will only have the option to grant the permission.
 * Defaults to false. Useful when the permission is mandatory for the app to work and the user will choose to grant the permission or quit the app.
 * @param acceptText: The text to display on the confirm button. Defaults to "Enable".
 * @param cancelText: The text to display on the later button. Defaults to "Later".
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
 * ){
 *  // you can provide your content here or use the default ones like below
 * }
 * ```
 *
 * Using the default content:
 *
 * ```
 * KwikPermissionsRequest(
 *    state = state,
 *    permissions = listOf(KwikPermissionDto(Manifest.permission.READ_EXTERNAL_STORAGE, "Allow app to access your photos and videos to use while creating a listing.")),
 *    title = "Permissions",
 *    deniedPermanentlyMessage = "Permission required. Go to settings to enable",
 *    onGrantAction = {
 *    // Handle permission granted
 *    },
 *    onDeniedAction = {
 *    // Handle permission denied
 *    },
 *    onCancel = {
 *    // Handle dialog cancel
 *    },
 *    mandatory = true,
 *    logo = {
 *    KwikImageView(
 *      url = yourLogoResource
 *    ),
 *    image = {
 *    KwikImageView(
 *      url = yourImageResource
 *    )
 *)
 * ```
 * */
@Composable
fun KwikPermissionsRequest(
    state: KwikPermissionRequest,
    permissions: List<KwikPermissionDto>,
    title: String,
    deniedPermanentlyMessage: String = "Permission required. Go to settings to enable",
    logo: @Composable () -> Unit = {},
    image: @Composable () -> Unit = {},
    onGrantAction: () -> Unit = {},
    onDeniedAction: () -> Unit = {},
    onCancel: () -> Unit = {},
    mandatory: Boolean = false,
    acceptText: String = "Enable",
    cancelText: String = "Cancel",
    content: @Composable (() -> Unit)? = null
) {
    val context = LocalContext.current
    var arePermissionsGranted by remember { mutableStateOf(context.isPermissionGranted(*permissions.map { it.permission }.toTypedArray())) }
    var permissionsExplanationDialogVisible by remember { mutableStateOf(!arePermissionsGranted) }
    val appPackageName = LocalContext.current.packageName
    var permissionRequestState by remember { mutableStateOf(state) }

    fun evaluatePermissions(){
        arePermissionsGranted = context.isPermissionGranted(*permissions.map { it.permission }.toTypedArray())
        if(arePermissionsGranted){
            permissionsExplanationDialogVisible = false
            onGrantAction()
        } else {
            permissionsExplanationDialogVisible = true
        }
        permissionRequestState = if(permissionRequestState.state == KwikPermissionRequestState.ShowRationale){
            KwikPermissionRequest(KwikPermissionRequestState.Requesting)
        } else {
            KwikPermissionRequest(KwikPermissionRequestState.Denied)
        }
    }

    LaunchedEffect(state) {
        permissionRequestState = state
        evaluatePermissions()
    }

    KwikComposableLifeCycle(
        onResume = {
            evaluatePermissions()
        }
    )

    KwikDialog.ContentDialog(
        open = permissionsExplanationDialogVisible,
        cancellable = !mandatory,
        dismiss = {
            permissionRequestState = KwikPermissionRequest(KwikPermissionRequestState.Denied)
            permissionsExplanationDialogVisible = false
            onCancel()
        }
    ) {
        KwikPermissionRequest(
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

        if(content != null){
            content.invoke()
        } else {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                logo()

                KwikText.TitleMedium(
                    text = title,
                    textAlign = TextAlign.Center
                )

                KwikText.TitleMedium(
                    text = permissions.firstOrNull()?.rationaleMessage ?: "Grant required permissions",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))

                image()

                if(permissionRequestState.state == KwikPermissionRequestState.Denied){
                    KwikCard(
                        modifier = Modifier.padding(12.dp),
                        containerColor = KwikColorWarning
                    ) {
                        KwikText.TitleMedium(
                            modifier = Modifier.padding(12.dp),
                            text = deniedPermanentlyMessage,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row {
                    if(!mandatory){
                        KwikTextButton(
                            text = cancelText,
                            onClick = {
                                permissionRequestState = KwikPermissionRequest(
                                    KwikPermissionRequestState.Denied
                                )
                                permissionsExplanationDialogVisible = false
                                onCancel()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    KwikButton(
                        text = acceptText,
                        onClick = {
                            if(permissionRequestState.state == KwikPermissionRequestState.Denied){
                                context.showInstalledAppDetails(appPackageName)
                            } else {
                                permissionRequestState = KwikPermissionRequest(
                                    KwikPermissionRequestState.Requesting
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

fun MutableState<KwikPermissionRequest>.requestPermissions() {
    this.value = KwikPermissionRequest(KwikPermissionRequestState.Requesting)
}

@Composable
fun rememberKwikPermissionState(): MutableState<KwikPermissionRequest> {
    return remember { mutableStateOf(KwikPermissionRequest(KwikPermissionRequestState.Requesting)) }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun KwikPermissionRequestPreview() {
    val state = rememberKwikPermissionState()

    KwikPermissionsRequest(
        state = state.value,
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
    permissionRequestState: KwikPermissionRequest,
    onPermissionRequestStateChange: (KwikPermissionRequest) -> Unit,
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
        onPermissionRequestStateChange(KwikPermissionRequest(newState))
    }

    LaunchedEffect(permissionRequestState) {
        when(permissionRequestState.state) {
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

data class KwikPermissionRequest(
    val state: KwikPermissionRequestState,
    val id: UUID = UUID.randomUUID()
)

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