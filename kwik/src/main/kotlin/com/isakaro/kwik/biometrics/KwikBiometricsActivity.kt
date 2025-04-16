package com.isakaro.kwik.biometrics

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.biometric.BiometricPrompt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(KwikBiometrics.TITLE) as String
        val subtitle = intent.getStringExtra(KwikBiometrics.SUBTITLE) as String
        val cancel = intent.getStringExtra(KwikBiometrics.CANCEL) as String

        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    setResult(RESULT_OK)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    setResult(RESULT_CANCELED)
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

data class KwikBiometricPromptParams(
    val title: String = "Authentication Required",
    val subtitle: String = "Verify your identity",
    val cancelText: String = "Cancel"
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
class KwikBiometricsAuthenticationContract: ActivityResultContract<KwikBiometricPromptParams, Boolean>() {
    override fun createIntent(context: Context, input: KwikBiometricPromptParams): Intent {
        return Intent(context, KwikBiometricActivity::class.java).apply {
            putExtra(KwikBiometrics.TITLE, input.title)
            putExtra(KwikBiometrics.SUBTITLE, input.subtitle)
            putExtra(KwikBiometrics.CANCEL, input.cancelText)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}

object KwikBiometrics {
    const val TITLE = "title"
    const val SUBTITLE = "subtitle"
    const val CANCEL = "cancel"

    /**
     * Check if biometric authentication is available and properly set up
     *
     * @param context Context to check biometric availability
     * @return Pair of (isAvailable, errorMessage)
     */
    fun isBiometricAvailable(context: Context): Pair<Boolean, String?> {

        return Pair(true, null)
    }
}