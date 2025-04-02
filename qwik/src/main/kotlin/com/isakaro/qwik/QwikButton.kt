package com.isakaro.qwik

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

enum class QwikButtonLoadingStyle {
    CIRCULAR,
    LINEAR
}

/**
 * A versatile button that can be customized to suit different use cases.
 * @param modifier Modifier to be applied to the button.
 * @param text The text to be displayed on the button. Can be a String, Int or AnnotatedString.
 * @param isLoading Whether the button is in a loading state.
 * @param loadingText The text to be displayed when the button is in a loading state.
 * @param outlined Whether the button should be outlined.
 * @param leadingIcon The icon to be displayed before the text. Can be an Int or ImageVector.
 * @param trailingIcon The icon to be displayed after the text. Can be an Int or ImageVector.
 * @param height The height of the button.
 * @param containerColor The color of the button.
 * @param tintIcon Whether the icon should be tinted with the button color.
 * @param allCaps Whether the text should be in all caps. Defaults to true.
 * @param enabled Whether the button is enabled.
 * @param fontStyle The style of the text. Refer to [TextStyle] and [MaterialTheme.typography].
 * @param qwikButtonLoadingStyle The style of the loading indicator. Can be [QwikButtonLoadingStyle.CIRCULAR] or [QwikButtonLoadingStyle.LINEAR].
 * @param onClick The action to be performed when the button is clicked.
 *
 * @sample com.isakaro.qwik.catalog.button
 * */
@Composable
fun QwikButton(
    modifier: Modifier = Modifier,
    text: Any,
    isLoading: Boolean = false,
    loadingText: String = "",
    outlined: Boolean = false,
    leadingIcon: Any? = null,
    trailingIcon: Any? = null,
    height: Int = 50,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    tintIcon: Boolean = true,
    allCaps: Boolean = true,
    enabled: Boolean = true,
    fontStyle: TextStyle = MaterialTheme.typography.titleSmall,
    qwikButtonLoadingStyle: QwikButtonLoadingStyle = QwikButtonLoadingStyle.CIRCULAR,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        border = if (outlined) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null,
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .alpha(if (isLoading) 0.5f else 1.0f)
            .clickable(
                indication = if(isLoading) null else LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ){

            }.then(modifier),
        colors = if(outlined) ButtonDefaults.outlinedButtonColors(contentColor = Color.White) else ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        contentPadding = PaddingValues(4.dp),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled && !isLoading,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        if(isLoading){
            if(qwikButtonLoadingStyle == QwikButtonLoadingStyle.CIRCULAR){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(color = Color.DarkGray, modifier = Modifier.size(30.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    if(loadingText.isNotBlank()) {
                        Text(
                            text = loadingText,
                            modifier = Modifier.fillMaxWidth(),
                            style = fontStyle
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if(loadingText.isNotBlank()){
                        Text(
                            text = loadingText,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            style = fontStyle
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Color.DarkGray,
                        trackColor = Color.Transparent
                    )
                }
            }
        } else {
            if(leadingIcon is Int){
                if (tintIcon){
                    Icon(
                        painter = painterResource(id = leadingIcon),
                        tint = if(outlined) MaterialTheme.colorScheme.primary else Color.White,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = leadingIcon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            } else if (leadingIcon is ImageVector) {
                if (tintIcon){
                    Icon(
                        imageVector = leadingIcon,
                        tint = if(outlined) MaterialTheme.colorScheme.primary else Color.White,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                } else {
                    Image(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            when (text) {
                is String -> {
                    Text(
                        text = if(allCaps) text.toString().uppercase() else text,
                        style = fontStyle,
                        color = if(outlined) MaterialTheme.colorScheme.primary else Color.White
                    )
                }
                is AnnotatedString -> {
                    Text(
                        text = text,
                        style = fontStyle,
                        color = if(outlined) MaterialTheme.colorScheme.primary else Color.White
                    )
                }
                is Int -> {
                    Text(
                        text = if(allCaps) stringResource(id = text).uppercase() else stringResource(id = text),
                        style = fontStyle,
                        color = if(outlined) MaterialTheme.colorScheme.primary else Color.White
                    )
                }
            }
            
            if(trailingIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            if(trailingIcon is Int){
                if (tintIcon){
                    Icon(
                        painter = painterResource(id = trailingIcon),
                        tint = if(outlined) MaterialTheme.colorScheme.primary else Color.White,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = trailingIcon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            } else if (trailingIcon is ImageVector) {
                if (tintIcon){
                    Icon(
                        imageVector = trailingIcon,
                        tint = if(outlined) MaterialTheme.colorScheme.primary else Color.White,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                } else {
                    Image(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}