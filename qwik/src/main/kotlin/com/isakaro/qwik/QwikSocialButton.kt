package com.isakaro.qwik

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics

import com.isakaro.logging.IsakaroEvents
import com.isakaro.networking.ApiClient
import com.isakaro.qwik.firebaseAnalytics
import com.isakaro.qwik.theme.ColorPrimaryAccent
import com.isakaro.qwik.utils.openURL

@Composable
fun QwikSocialButton(
    icon: Int,
    text: String,
    contentColor: Color = Color.Black,
    containerColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        border = BorderStroke(1.dp, ColorPrimaryAccent),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ){

            },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(8.dp),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Image(
                modifier = Modifier.size(35.dp).align(Alignment.CenterStart),
                painter = painterResource(id = icon),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
                color = contentColor
            )
        }
    }
}

@Composable
fun QwikSocialButtonGroup(
    firebaseAnalytics: FirebaseAnalytics = firebaseAnalytics(),
){

    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.weight(1f),
            border = BorderStroke(1.dp, Color.LightGray),
            onClick = {
                context.openURL(ApiClient.Oauth2.GOOGLE_AUTH_REQUEST)

                firebaseAnalytics.logEvent(
                    IsakaroEvents.ACTION,
                    bundleOf(
                        IsakaroEvents.ACTION_NAME to "click_login_google"
                    )
                )
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }

        Button(
            modifier = Modifier.weight(1f),
            border = BorderStroke(1.dp, Color.LightGray),
            onClick = {
                context.openURL(ApiClient.Oauth2.FACEBOOK_AUTH_REQUEST)

                firebaseAnalytics.logEvent(
                    IsakaroEvents.ACTION,
                    bundleOf(
                        IsakaroEvents.ACTION_NAME to "click_login_facebook"
                    )
                )
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.facebook_logo),
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

@Composable
fun QwikEmailButton(
    text: String,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        border = BorderStroke(1.dp, ColorPrimaryAccent),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ){

            },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = ColorPrimaryAccent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(8.dp),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Icon(
                modifier = Modifier.size(35.dp).align(Alignment.CenterStart),
                painter = painterResource(id = R.drawable.email),
                tint = Color.White,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun QwikSocialButtonPreview() {
    IsakaroSocialButton(
        icon = R.drawable.google_logo,
        text = "Google"
    )
}

@Preview
@Composable
fun QwikEmailButtonPreview() {
    IsakaroEmailButton(
        text = "E-Mail"
    )
}

@Preview
@Composable
fun QwikSocialButtonGroupPreview() {
    IsakaroSocialButtonGroup()
}