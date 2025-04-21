package com.isakaro.kwik.ui.biometrics

import android.content.Context
import android.content.Intent
import androidx.biometric.BiometricPrompt
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.biometric.BiometricManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.isakaro.R
import com.isakaro.kwik.ui.button.KwikTextButton
import com.isakaro.kwik.ui.helpers.KwikCenterColumn

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
        val biometricsLevel = intent.getIntExtra(KwikBiometrics.BIOMETRICS_LEVEL, BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        var failedCount = 0

        if(!biometricManager.hasBiometricsCapability(biometricsLevel)){
            setResult(KwikBiometricsResult.NOT_SET)
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
                    if(errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON || errorCode == BiometricPrompt.ERROR_USER_CANCELED){
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
            Surface(
                color = if(isSystemInDarkTheme()) Color.Black else MaterialTheme.colorScheme.background,
            ) {
                KwikCenterColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.fingerprint),
                        contentDescription = "biometrics authentication",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(bottom = 4.dp)
                    )

                    KwikTextButton(
                        text = "Cancel Verification",
                        onClick = {
                            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }

    private fun BiometricManager.hasBiometricsCapability(biometricsLevel: Int): Boolean {
        return this.canAuthenticate(biometricsLevel) == BiometricManager.BIOMETRIC_SUCCESS
    }
}

data class KwikBiometricPromptParams(
    val title: String = "Authentication Required",
    val subtitle: String = "Verify your identity",
    val cancelText: String = "Cancel",
    val biometricsLevel: Int = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL
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
    const val NOT_SET = 404
}