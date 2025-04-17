package com.isakaro.kwik.catalog.biometrics

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.isakaro.Kwik.catalog.R
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.biometrics.KwikBiometricPromptParams
import com.isakaro.kwik.biometrics.KwikBiometricsAuthenticationContract
import com.isakaro.kwik.biometrics.KwikBiometricsResult
import com.isakaro.kwik.button.KwikButton
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.helpers.KwikCenterColumn
import com.isakaro.kwik.navigator
import com.isakaro.kwik.theme.KwikColorError
import com.isakaro.kwik.theme.KwikColorSuccess
import com.isakaro.kwik.toast.KwikToast
import com.isakaro.kwik.toast.rememberKwikToastState
import com.isakaro.kwik.toast.showToast
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
fun KwikBiometricsScreen(
    navigator: DestinationsNavigator = navigator()
) {
    val kwikToastState = rememberKwikToastState()

    val launcher = rememberLauncherForActivityResult(KwikBiometricsAuthenticationContract()) { result ->
        when(result){
            KwikBiometricsResult.SUCCESS -> kwikToastState.showToast("Biometric authentication successful", backgroundColor = KwikColorSuccess)
            KwikBiometricsResult.FAILED -> kwikToastState.showToast("Biometric authentication failed", backgroundColor = KwikColorError)
            KwikBiometricsResult.ERROR -> kwikToastState.showToast("Biometric authentication error", backgroundColor = KwikColorError)
            KwikBiometricsResult.CANCELED -> kwikToastState.showToast("Biometric authentication canceled")
            KwikBiometricsResult.NO_HARDWARE -> kwikToastState.showToast("No biometric hardware found", backgroundColor = KwikColorError)
        }
    }

    KwikToast(state = kwikToastState)

    ShowCaseContainer(
        title = "Biometric Authentication",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase {
            KwikCenterColumn {
                KwikButton(
                    text = "VERIFY YOUR IDENTITY",
                    leadingIcon = R.drawable.biometrics
                ) {
                    launcher.launch(
                        KwikBiometricPromptParams(
                            title = "Verify Identity",
                            subtitle = "Authentication required to continue",
                            cancelText = "Cancel",
                            biometricsLevel = BiometricManager.Authenticators.IDENTITY_CHECK
                        )
                    )
                }
            }
        }
    }

}
