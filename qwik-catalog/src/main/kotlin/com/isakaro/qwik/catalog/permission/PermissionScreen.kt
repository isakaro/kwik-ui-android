package com.isakaro.qwik.catalog.permission

import android.Manifest
import android.os.Build
import android.webkit.PermissionRequest
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
import com.isakaro.qwik.QwikPermissionDto
import com.isakaro.qwik.QwikPermissionsRequest
import com.isakaro.qwik.QwikToast
import com.isakaro.qwik.QwikToastType
import com.isakaro.qwik.R
import com.isakaro.qwik.catalog.ShowCaseContainer
import com.isakaro.qwik.rememberQwikToastState
import com.isakaro.qwik.showToast
import com.isakaro.qwik.theme.Theme.QwikTheme

@Composable
internal fun PermissionsScreen() {

    val qwikToastState = rememberQwikToastState()
    var granted by remember { mutableStateOf(false) }

    if (qwikToastState.value.isVisible) {
        QwikToast(state = qwikToastState)
    }

    ShowCaseContainer {
        QwikPermissionsRequest(
            mandatory = true,
            permissions = when {
                Build.VERSION.SDK_INT >= 33 -> {
                    listOf(
                        QwikPermissionDto(Manifest.permission.READ_MEDIA_IMAGES, "Allow app to access your photos and videos to use while creating a listing."),
                        QwikPermissionDto(Manifest.permission.READ_MEDIA_VIDEO, "Allow app to access your photos and videos to use while creating a listing.")
                    )
                }
                else -> {
                    listOf(
                        QwikPermissionDto(Manifest.permission.READ_EXTERNAL_STORAGE, "Allow app to access your photos and videos to use while creating a listing.")
                    )
                }
            },
            title = "Media access required",
            icon = R.drawable.shield,
            iconTint = Color.Black,
            onGrantAction = {
                granted = true
            },
            onDeniedAction = {
                qwikToastState.showToast(message = "These permissions are necessary for the feature to function", type = QwikToastType.WARNING)
            },
            onCancel = {
                qwikToastState.showToast(message = "These permissions are necessary for the feature to function", type = QwikToastType.WARNING)
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    QwikTheme {
        PermissionsScreen()
    }
}
