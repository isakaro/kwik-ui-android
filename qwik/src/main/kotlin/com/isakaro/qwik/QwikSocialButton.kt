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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class QwikSocialPlatform {
    GOOGLE,
    APPLE,
    FACEBOOK
}

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
    enabled: List<QwikSocialPlatform> = listOf(QwikSocialPlatform.GOOGLE, QwikSocialPlatform.APPLE, QwikSocialPlatform.FACEBOOK),
    onClick: (QwikSocialPlatform) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(enabled.contains(QwikSocialPlatform.GOOGLE)) {
            QwikSocialButton(
                icon = R.drawable.google_logo,
                text = "Google",
                onClick = {
                    onClick(QwikSocialPlatform.GOOGLE)
                }
            )
        }

        if(enabled.contains(QwikSocialPlatform.APPLE)) {
            QwikSocialButton(
                icon = R.drawable.apple_logo,
                text = "Apple",
                onClick = {
                    onClick(QwikSocialPlatform.APPLE)
                }
            )
        }

        if(enabled.contains(QwikSocialPlatform.FACEBOOK)) {
            QwikSocialButton(
                icon = R.drawable.facebook_logo,
                text = "Facebook",
                onClick = {
                    onClick(QwikSocialPlatform.FACEBOOK)
                }
            )
        }
    }
}

@Composable
fun QwikSimpleSocialButtonGroup(
    enabled: List<QwikSocialPlatform> = listOf(QwikSocialPlatform.GOOGLE, QwikSocialPlatform.APPLE, QwikSocialPlatform.FACEBOOK),
    onClick: (QwikSocialPlatform) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(enabled.contains(QwikSocialPlatform.GOOGLE)) {
            Button(
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, Color.LightGray),
                onClick = {
                    onClick(QwikSocialPlatform.GOOGLE)
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

        if(enabled.contains(QwikSocialPlatform.APPLE)) {
            Button(
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, Color.LightGray),
                onClick = {
                    onClick(QwikSocialPlatform.APPLE)
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

        if(enabled.contains(QwikSocialPlatform.FACEBOOK)) {
            Button(
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, Color.LightGray),
                onClick = {
                    onClick(QwikSocialPlatform.FACEBOOK)
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
fun QwikSocialButtonPreview() {
    QwikSocialButton(
        icon = R.drawable.google_logo,
        text = "Google"
    )
}

@Preview
@Composable
fun QwikSocialButtonGroupPreview() {
    QwikSocialButtonGroup(onClick = {})
}

@Preview
@Composable
fun QwikSimpleSocialButtonGroupPreview() {
    QwikSimpleSocialButtonGroup(onClick = {})
}