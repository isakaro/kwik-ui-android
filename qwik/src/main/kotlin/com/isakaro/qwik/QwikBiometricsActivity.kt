package com.isakaro.qwik

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.biometric.BiometricPrompt
import android.os.Bundle
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object QwikBiometrics {
    const val TITLE = "title"
    const val SUBTITLE = "subtitle"
    const val CANCEL = "cancel"
}

class QwikBiometricActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(QwikBiometrics.TITLE) as String
        val subtitle = intent.getStringExtra(QwikBiometrics.SUBTITLE) as String
        val cancel = intent.getStringExtra(QwikBiometrics.CANCEL) as String

        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
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
            Column(
                modifier = Modifier.fillMaxSize().background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QwikText.TitleText(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "Verify your identity",
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.titleMedium
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

class QwikBiometricsAuthenticationContract: ActivityResultContract<Unit, Boolean>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, QwikBiometricActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}