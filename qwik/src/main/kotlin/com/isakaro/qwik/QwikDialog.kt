package com.isakaro.qwik

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * This object contains functions that can be used to display dialogs in a Qwik way.
 * [BaseDialog] is the base dialog that is used by the other dialog functions.
 * [ContentDialog] is a modular dialog that can be used to display content or confirm actions.
 * [ConfirmDialog] is a confirm dialog that can be used to confirm actions.
 * [GalleryDialog] is a dialog that can be used to display a gallery of images or other content that require a full screen view.
 * */
object QwikDialog {

    /**
     * Base dialog that is used by the other dialog functions.
     *
     * @param modifier Modifier to be applied to the dialog.
     * @param title The title of the dialog.
     * @param cancellable Whether the dialog can be dismissed by clicking outside of it.
     * @param withCloseIcon Whether the dialog should have a close icon.
     * @param onDismiss The function to be called when the dialog is dismissed.
     * @param content The content of the dialog.
     *
     * */
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
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "close",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * A modular dialog that can be used to display content or confirm actions.
     *
     * @param modifier Modifier to be applied to the dialog.
     * @param open Whether the dialog should be displayed.
     * @param title The title of the dialog.
     * @param cancellable Whether the dialog can be dismissed by clicking outside of it.
     * @param withCloseIcon Whether the dialog should have a close icon.
     * @param dismiss The function to be called when the dialog is dismissed.
     * @param content The content of the dialog.
     * */
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

    /**
     * A confirm dialog that can be used to confirm actions.
     *
     * @param modifier Modifier to be applied to the dialog.
     * @param open Whether the dialog should be displayed.
     * @param title The title of the dialog.
     * @param cancellable Whether the dialog can be dismissed by clicking outside of it.
     * @param onConfirm The function to be called when the confirm button is clicked.
     * @param onCancel The function to be called when the cancel button is clicked.
     * @param dismiss The function to be called when the dialog is dismissed.
     * @param isLoading Whether the confirm button should display a loading indicator.
     * @param confirmButtonVisible Whether the confirm button should be visible.
     * @param confirmColor The color of the confirm button.
     * @param confirmText The text of the confirm button.
     * @param cancelText The text of the cancel button.
     * @param content The content of the dialog.
     *
     * Example usage:
     *
     * ```
     * ConfirmDialog(
     *    open = open,
     *    title = "Are you sure?",
     *    confirmText = "Yes I'm sure, cancel",
     *    cancelText = "No, stay",
     *    onConfirm = {
     *    // Confirm action
     *    },
     *    dismiss = {
     *    // Dismiss action
     *    }
     * ) {
     *    // Your content
     *  }
     *  ```
     * @see ContentDialog for including your own content in a dialog without confirm buttons.
     */
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
        confirmColor: Color = MaterialTheme.colorScheme.primary,
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
                        QwikTextButton(
                            modifier = Modifier.weight(0.4f),
                            text = cancelText ?: "Cancel",
                            containerColor = Color.Black,
                            onClick = {
                                onCancel()
                                dismiss()
                            }
                        )
                    }

                    if(confirmButtonVisible){
                        QwikButton(
                            modifier = Modifier.weight(0.6f),
                            text = confirmText ?: "Confirm",
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

    /**
     * A dialog that can be used to display a gallery of images or other content that require a full screen view.
     *
     * @param open Whether the dialog should be displayed.
     * @param title The title of the dialog.
     * @param cancellable Whether the dialog can be dismissed by clicking outside of it.
     * @param dismiss The function to be called when the dialog is dismissed.
     * @param content The content of the dialog.
     *
     * Example usage:
     *
     * ```
     * GalleryDialog(
     *   open = open,
     *   title = "Gallery",
     *   dismiss = {
     *   // Dismiss action
     *   },
     *   content = {
     *   // Your content
     *   }
     * )
     * */
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
                    QwikText.TitleText(
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
                            imageVector = Icons.Default.Close,
                            contentDescription = "close",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

}