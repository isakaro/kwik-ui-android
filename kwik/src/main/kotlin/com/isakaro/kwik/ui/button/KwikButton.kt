package com.isakaro.kwik.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.R
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.loading.KwikCircularLoading
import com.isakaro.kwik.ui.spacer.KwikHSpacer
import com.isakaro.kwik.ui.text.KwikText

enum class KwikButtonLoadingStyle {
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
 * @param enabled Whether the button is enabled.
 * @param fontStyle The style of the text. Refer to [TextStyle] and [MaterialTheme.typography].
 * @param kwikButtonLoadingStyle The style of the loading indicator. Can be [KwikButtonLoadingStyle.CIRCULAR] or [KwikButtonLoadingStyle.LINEAR].
 * @param onClick The action to be performed when the button is clicked.
 *
 * Example usage:
 *
 * ```
 * KwikButton(
 *    text = "Click me",
 *    onClick = { /* Do something */ }
 * )
 *```
 *
 * ```
 * KwikButton(
 *   text = stringResource(id = R.string.click_me),
 *   onClick = { /* Do something */ },
 *   outlined = true,
 *   leadingIcon = Icons.Default.Add,
 *   loadingText = "Loading...",
 *   isLoading = true,
 *)
 * */
@Composable
fun KwikButton(
    modifier: Modifier = Modifier,
    text: Any,
    isLoading: Boolean = false,
    loadingText: String = "",
    outlined: Boolean = false,
    leadingIcon: Any? = null,
    trailingIcon: Any? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    tintIcon: Boolean = true,
    enabled: Boolean = true,
    fontStyle: TextStyle = MaterialTheme.typography.titleSmall,
    kwikButtonLoadingStyle: KwikButtonLoadingStyle = KwikButtonLoadingStyle.CIRCULAR,
    shape: Shape = MaterialTheme.shapes.medium,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(2.dp),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        border = if (outlined) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else border,
        modifier = Modifier
            .heightIn(40.dp)
            .alpha(if (isLoading) 0.5f else 1.0f)
            .clickable(
                indication = if (isLoading) null else LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) {

            }
            .then(modifier),
        colors = if(outlined) ButtonDefaults.outlinedButtonColors(contentColor = Color.White) else ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        contentPadding = contentPadding,
        shape = shape,
        enabled = enabled && !isLoading,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        if(isLoading){
            if(kwikButtonLoadingStyle == KwikButtonLoadingStyle.CIRCULAR){
                Box {
                    KwikCircularLoading(
                        color = if(isSystemInDarkTheme()) Color.White else Color.DarkGray,
                        modifier = Modifier.size(30.dp).align(Alignment.CenterStart)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(loadingText.isNotBlank()) {
                            KwikText.RenderText(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = loadingText,
                                style = fontStyle
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if(loadingText.isNotBlank()){
                        KwikText.RenderText(
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
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        color = if(isSystemInDarkTheme()) Color.White else Color.DarkGray,
                        trackColor = Color.Transparent
                    )
                }
            }
        } else {
            if(leadingIcon != null){
                KwikImageView(
                    url = leadingIcon,
                    tint = if (tintIcon) {
                        if(outlined) MaterialTheme.colorScheme.primary else Color.White
                    } else {
                        Color.Unspecified
                    },
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            KwikText.RenderText(
                modifier = Modifier.alpha(if (!enabled) 0.5f else 1f),
                text = text,
                style = fontStyle,
                color = if (outlined) MaterialTheme.colorScheme.primary else Color.White
            )

            if(trailingIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
            }

            if(trailingIcon != null){
                KwikImageView(
                    url = trailingIcon,
                    tint = if (tintIcon) {
                        if(outlined) MaterialTheme.colorScheme.primary else Color.White
                    } else {
                        Color.Unspecified
                    },
                )
            }

            KwikHSpacer(8)
        }
    }
}

/**
 * An extended floating action button that can be customized to suit different use cases.
 *
 * @param modifier Modifier to be applied to the button.
 * @param text The text to be displayed on the button.
 * @param containerColor The color of the button.
 * @param icon The icon to be displayed on the button.
 * @param contentColor The color of the icon.
 * @param shape The shape of the button.
 * @param expanded Whether the button is expanded.
 * @param elevation The elevation of the button.
 * @param interactionSource The interaction source of the button.
 * @param onClick The action to be performed when the button is clicked.
 *
 * Example usage:
 *
 * ```
 * KwikExtendedFloatingActionButton(
 *    text = { Text(text = "Action", style = MaterialTheme.typography.titleSmall) },
 *    icon = { Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share") },
 *    containerColor = MaterialTheme.colorScheme.primary,
 *    onClick = { /* Do something */ }
 * )
 * ```
 * */
@Composable
fun KwikExtendedFloatingActionButton(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = FloatingActionButtonDefaults.extendedFabShape,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource? = null,
    icon: @Composable () -> Unit = {},
    loading: Boolean = false,
    loadingText: String? = null,
    expanded: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = modifier.alpha(if(!enabled || loading) 0.5f else 1f),
        text = {
            if(loading){
                KwikCircularLoading(
                    trackColor = contentColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if(loadingText != null){
                    KwikText.TitleSmall(
                        text = loadingText,
                        color = contentColor
                    )
                }
            } else {
                text()
            }
        },
        icon = {
            if(!loading){
              icon()
            }
        },
        shape = shape,
        containerColor = containerColor,
        elevation = elevation,
        interactionSource = interactionSource,
        expanded = expanded,
        onClick = {
            if(enabled && !loading) onClick()
        }
    )
}

/**
 * A floating action button that can be customized to suit different use cases.
 *
 * @param modifier Modifier to be applied to the button.
 * @param containerColor The color of the button.
 * @param contentColor The color of the icon.
 * @param shape The shape of the button.
 * @param loading Whether the button is in a loading state.
 * @param loadingText The text to be displayed when the button is in a loading state.
 * @param enabled Whether the button is enabled.
 * @param elevation The elevation of the button.
 * @param interactionSource The interaction source of the button.
 * @param onClick The action to be performed when the button is clicked.
 * @param content The content of the button.
 *
 * Example usage:
 *
 * ```
 * KwikExtendedFloatingActionButton(
 *    text = { Text(text = "Action", style = MaterialTheme.typography.titleSmall) },
 *    icon = { Icon(Icons.Sharp.Share, tint = Color.White, contentDescription = "share") },
 *    containerColor = MaterialTheme.colorScheme.primary,
 *    onClick = { /* Do something */ }
 * )
 * ```
 * */
@Composable
fun KwikFloatingActionButton(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = FloatingActionButtonDefaults.extendedFabShape,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    loading: Boolean = false,
    loadingText: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    FloatingActionButton(
        modifier = modifier.alpha(if(!enabled || loading) 0.5f else 1f),
        contentColor = contentColor,
        containerColor = containerColor,
        shape = shape,
        elevation = elevation,
        interactionSource = interactionSource,
        onClick = { if (enabled && !loading) onClick() },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (loading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    KwikCircularLoading(
                        trackColor = contentColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if(loadingText != null){
                        KwikText.TitleSmall(
                            text = loadingText,
                            color = contentColor
                        )
                    }
                }
            } else {
                content()
            }
        }
    }
}

@Preview
@Composable
fun KwikButtonPreview() {
    KwikButton(
        text = "Click me",
        onClick = {  }
    )
}

@Preview
@Composable
fun KwikButtonWithLeadingIconPreview() {
    KwikButton(
        text = "Click me",
        leadingIcon = Icons.AutoMirrored.Filled.ArrowForward,
        onClick = {  }
    )
}

@Preview
@Composable
fun KwikButtonWithTrailingIconPreview() {
    KwikButton(
        text = "Click me",
        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
        onClick = {  }
    )
}