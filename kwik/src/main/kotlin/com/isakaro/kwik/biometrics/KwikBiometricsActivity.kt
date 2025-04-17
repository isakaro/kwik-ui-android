package com.isakaro.kwik.biometrics

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.biometric.BiometricPrompt
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.biometric.BiometricManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.R
import com.isakaro.kwik.helpers.KwikCenterColumn
import com.isakaro.kwik.theme.KwikTheme

/**
 * Handles biometric authentication in an activity
 * */
class KwikBiometricActivity : FragmentActivity() {

    private val biometricManager by lazy { BiometricManager.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(KwikBiometrics.TITLE) as String
        val subtitle = intent.getStringExtra(KwikBiometrics.SUBTITLE) as String
        val cancel = intent.getStringExtra(KwikBiometrics.CANCEL) as String
        val biometricsLevel = intent.getIntExtra(KwikBiometrics.BIOMETRICS_LEVEL, BiometricManager.Authenticators.BIOMETRIC_STRONG)
        var failedCount = 0

        if(!biometricManager.hasBiometricsCapability(biometricsLevel)){
            setResult(KwikBiometricsResult.NO_HARDWARE)
            finish()
        }

        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    setResult(KwikBiometricsResult.SUCCESS)
                    finish()
                }
                override fun onAuthenticationFailed() {
                    if(failedCount >= 3){
                        setResult(KwikBiometricsResult.FAILED)
                        finish()
                    }
                    failedCount+=1
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    if(errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON){
                        setResult(KwikBiometricsResult.CANCELED)
                    } else {
                        setResult(KwikBiometricsResult.ERROR)
                    }
                    finish()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setNegativeButtonText(cancel)
            .build()

        biometricPrompt.authenticate(promptInfo)

        setContent {
            KwikTheme {
                Surface {
                    KwikCenterColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        KwikText.TitleMedium(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = "Verify your identity",
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = {
                                biometricPrompt.authenticate(promptInfo)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.fingerprint),
                                contentDescription = "biometrics authentication",
                                tint = Color.Black,
                                modifier = Modifier.size(60.dp).padding(end = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun BiometricManager.hasBiometricsCapability(biometricsLevel: Int = BiometricManager.Authenticators.BIOMETRIC_STRONG): Boolean {
        return this.canAuthenticate(biometricsLevel) == BiometricManager.BIOMETRIC_SUCCESS
    }
}

data class KwikBiometricPromptParams(
    val title: String = "Authentication Required",
    val subtitle: String = "Verify your identity",
    val cancelText: String = "Cancel",
    val biometricsLevel: Int = BiometricManager.Authenticators.BIOMETRIC_STRONG
)

/**
 * The contract for biometric authentication.
 *
 * Usage:
 * ```
 *     val launcher = rememberLauncherForActivityResult(KwikBiometricsAuthenticationContract()) { success ->
 *         if (success) {
 *             // handle success
 *         } else {
 *             // handle error
 *         }
 *     }
 *
 *     launcher.launch(
 *                 KwikBiometricPromptParams(
 *                     title = "Verify Identity",
 *                     subtitle = "Authentication required to continue",
 *                     cancelText = "Cancel"
 *                 )
 *     )
 * ```
 * */
class KwikBiometricsAuthenticationContract: ActivityResultContract<KwikBiometricPromptParams, Int>() {
    override fun createIntent(context: Context, input: KwikBiometricPromptParams): Intent {
        return Intent(context, KwikBiometricActivity::class.java).apply {
            putExtra(KwikBiometrics.TITLE, input.title)
            putExtra(KwikBiometrics.SUBTITLE, input.subtitle)
            putExtra(KwikBiometrics.CANCEL, input.cancelText)
            putExtra(KwikBiometrics.BIOMETRICS_LEVEL, input.biometricsLevel)
        }
    }
    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        return resultCode
    }
}

object KwikBiometrics {
    const val TITLE = "title"
    const val SUBTITLE = "subtitle"
    const val CANCEL = "cancel"
    const val BIOMETRICS_LEVEL = "biometrics_level"
}

object KwikBiometricsResult {
    const val SUCCESS = -1
    const val CANCELED = 0
    const val FAILED = 401
    const val ERROR = 400
    const val NO_HARDWARE = 404
}