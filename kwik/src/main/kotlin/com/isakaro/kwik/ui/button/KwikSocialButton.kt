package com.isakaro.kwik.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.R
import com.isakaro.kwik.ui.text.KwikText

enum class KwikSocialPlatform {
    GOOGLE,
    APPLE,
    FACEBOOK
}

/**
 * Social button for Google, Apple and Facebook
 *
 * @param icon: Int - The icon resource
 * @param text: String - The text to display
 * @param contentColor: Color - The color of the text
 * @param containerColor: Color - The color of the container
 * @param onClick: () -> Unit - The click listener
 * */
@Composable
fun KwikSocialButton(
    icon: Int,
    text: String,
    contentColor: Color = Color.Black,
    containerColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
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
            KwikText.TitleSmall(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

@Composable
fun KwikSocialButtonGroup(
    enabled: List<KwikSocialPlatform> = listOf(
        KwikSocialPlatform.GOOGLE,
        KwikSocialPlatform.APPLE,
        KwikSocialPlatform.FACEBOOK
    ),
    onClick: (KwikSocialPlatform) -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if(enabled.contains(KwikSocialPlatform.GOOGLE)) {
            KwikSocialButton(
                icon = R.drawable.google_logo,
                text = "Google",
                onClick = {
                    onClick(KwikSocialPlatform.GOOGLE)
                }
            )
        }

        if(enabled.contains(KwikSocialPlatform.APPLE)) {
            KwikSocialButton(
                icon = R.drawable.apple_logo,
                text = "Apple",
                onClick = {
                    onClick(KwikSocialPlatform.APPLE)
                }
            )
        }

        if(enabled.contains(KwikSocialPlatform.FACEBOOK)) {
            KwikSocialButton(
                icon = R.drawable.facebook_logo,
                text = "Facebook",
                onClick = {
                    onClick(KwikSocialPlatform.FACEBOOK)
                }
            )
        }
    }
}

@Composable
fun KwikSimpleSocialButtonGroup(
    enabled: List<KwikSocialPlatform> = listOf(
        KwikSocialPlatform.GOOGLE,
        KwikSocialPlatform.APPLE,
        KwikSocialPlatform.FACEBOOK
    ),
    onClick: (KwikSocialPlatform) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(enabled.contains(KwikSocialPlatform.GOOGLE)) {
            Button(
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, Color.LightGray),
                onClick = {
                    onClick(KwikSocialPlatform.GOOGLE)
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
        }

        if(enabled.contains(KwikSocialPlatform.APPLE)) {
            Button(
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, Color.LightGray),
                onClick = {
                    onClick(KwikSocialPlatform.APPLE)
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apple_logo),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        }

        if(enabled.contains(KwikSocialPlatform.FACEBOOK)) {
            Button(
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, Color.LightGray),
                onClick = {
                    onClick(KwikSocialPlatform.FACEBOOK)
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
}

@Preview
@Composable
private fun KwikSocialButtonPreview() {
    KwikSocialButton(
        icon = R.drawable.google_logo,
        text = "Google"
    )
}

@Preview
@Composable
private fun KwikSocialButtonGroupPreview() {
    KwikSocialButtonGroup(onClick = {})
}

@Preview
@Composable
private fun KwikSimpleSocialButtonGroupPreview() {
    KwikSimpleSocialButtonGroup(onClick = {})
}