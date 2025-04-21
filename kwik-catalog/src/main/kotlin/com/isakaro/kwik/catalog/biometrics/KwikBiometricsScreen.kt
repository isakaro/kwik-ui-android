package com.isakaro.kwik.catalog.biometrics

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.biometrics.KwikBiometricPromptParams
import com.isakaro.kwik.ui.biometrics.KwikBiometricsAuthenticationContract
import com.isakaro.kwik.ui.biometrics.KwikBiometricsResult
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.helpers.KwikCenterColumn
import com.isakaro.kwik.ui.theme.KwikColorError
import com.isakaro.kwik.ui.theme.KwikColorSuccess
import com.isakaro.kwik.ui.toast.KwikToast
import com.isakaro.kwik.ui.toast.rememberKwikToastState
import com.isakaro.kwik.ui.toast.showToast
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
            KwikBiometricsResult.NOT_SET -> kwikToastState.showToast("No biometrics set", backgroundColor = KwikColorError)
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
                            cancelText = "Cancel"
                        )
                    )
                }
            }
        }
    }

}
