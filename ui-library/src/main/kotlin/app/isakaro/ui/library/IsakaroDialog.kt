package com.isakaro.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.isakaro.R
import com.isakaro.ui.theme.ColorPrimaryAccent

object IsakaroDialog {
    @Composable
    private fun BaseDialog(
        modifier: Modifier = Modifier,
        title: String? = null,
        cancellable: Boolean = true,
        withCloseIcon: Boolean = false,
        onDismiss: () -> Unit,
        content: @Composable () -> Unit
    ) {
        Dialog(
            onDismissRequest = {
                onDismiss()
            },
            properties = DialogProperties(
                dismissOnBackPress = cancellable,
                dismissOnClickOutside = cancellable,
                usePlatformDefaultWidth = false
            )
        ) {
            AnimatedVisibility(
                visible = true,
                enter = expandIn(
                    expandFrom = Alignment.Center,
                    initialSize = { IntSize(0, 0) },
                    animationSpec = tween(
                        durationMillis = 2000,
                        easing = FastOutSlowInEasing
                    )
                ),
                exit = shrinkOut(
                    shrinkTowards = Alignment.Center,
                    animationSpec = tween(
                        durationMillis = 2000,
                        easing = FastOutSlowInEasing
                    )
                )
            ) {
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .then(modifier),
                    color = Color.Transparent
                ) {
                    Box {
                        Column(
                            modifier = Modifier
                                .heightIn(min = 150.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                        ) {
                            if (title != null) {
                                Text(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            }

                            content()
                        }
                        if(withCloseIcon){
                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd),
                                onClick = {
                                    onDismiss()
                                },
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(30.dp),
                                    painter = painterResource(id = R.drawable.close),
                                    contentDescription = stringResource(id = R.string.close),
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ContentDialog(
        modifier: Modifier = Modifier,
        open: Boolean,
        title: String? = null,
        cancellable: Boolean = true,
        withCloseIcon: Boolean = false,
        dismiss: () -> Unit,
        content: @Composable () -> Unit
    ){
        if(open){
            BaseDialog(
                modifier = modifier,
                title = title,
                cancellable = cancellable,
                withCloseIcon = withCloseIcon,
                onDismiss = {
                    dismiss()
                }
            ) {
                content()
            }
        }
    }

    @Composable
    fun ConfirmDialog(
        modifier: Modifier = Modifier,
        open: Boolean,
        title: String? = null,
        cancellable: Boolean = true,
        onConfirm: () -> Unit,
        onCancel: () -> Unit = {},
        dismiss: () -> Unit,
        isLoading: Boolean = false,
        confirmButtonVisible: Boolean = true,
        confirmColor: Color = ColorPrimaryAccent,
        confirmText: String? = null,
        cancelText: String? = null,
        content: @Composable () -> Unit
    ){
        if(open){
            BaseDialog(
                modifier = modifier,
                title = title,
                cancellable = cancellable,
                onDismiss = {
                    dismiss()
                }
            ) {
                content()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(cancellable){
                        TextButton(
                            modifier = Modifier.weight(0.4f),
                            onClick = {
                                onCancel()
                                dismiss()
                            }
                        ) {
                            Text(
                                text = cancelText ?: stringResource(id = R.string.cancel),
                                color = Color.Black
                            )
                        }
                    }

                    if(confirmButtonVisible){
                        IsakaroButton(
                            modifier = Modifier.weight(0.6f),
                            text = confirmText ?: stringResource(id = R.string.confirm),
                            containerColor = confirmColor,
                            height = 40,
                            isLoading = isLoading
                        ) {
                            onConfirm()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun GalleryDialog(
        open: Boolean,
        title: String? = null,
        cancellable: Boolean = true,
        dismiss: () -> Unit,
        content: @Composable () -> Unit
    ) {
        if (open) {
            Dialog(
                onDismissRequest = { dismiss() },
                properties = DialogProperties(
                    dismissOnBackPress = cancellable,
                    dismissOnClickOutside = cancellable,
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    IsakaroText.TitleText(
                        text = title ?: "",
                        color = Color.White,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopCenter)
                    )
                    content()

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        onClick = {
                            dismiss()
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = stringResource(id = R.string.close),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

}