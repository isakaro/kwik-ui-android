package com.isakaro.kwik.ui.inputfields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.R
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.button.KwikTextButton
import com.isakaro.kwik.ui.counter.KwikCounter
import com.isakaro.kwik.ui.dialog.KwikDialog
import com.isakaro.kwik.ui.spacer.KwikHSpacer
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.theme.KwikColorFilledTextFieldFocused
import com.isakaro.kwik.ui.theme.KwikColorFilledTextFieldFocusedDarkMode
import com.isakaro.kwik.ui.theme.KwikColorFilledTextFieldUnfocused
import com.isakaro.kwik.ui.theme.KwikColorFilledTextFieldUnfocusedDarkMode
import com.isakaro.kwik.ui.toast.KwikToast
import com.isakaro.kwik.ui.toast.rememberKwikToastState
import com.isakaro.kwik.ui.toast.showToast
import com.isakaro.kwik.ui.utils.text
import java.util.UUID

/**
 * Holds the data for each tag item.
 *
 * @param id: Unique identifier for the tag.
 * @param label: The display name of the tag.
 * @param quantity: The quantity of the tag. Default is 1.
 * @param minQuantity: The minimum quantity allowed. Default is 1.
 * @param maxQuantity: The maximum quantity allowed. Default is 100.
 * */
data class KwikTagsInputItem(
    val id: Any,
    val label: String,
    val quantity: Int = 1,
    val minQuantity: Int = 1,
    val maxQuantity: Int = 100
)

/**
 * A component that allows the user to input tags with optional quantity.
 *
 * @param items: List of available tags to choose from.
 * @param placeholder: Placeholder text for the input field.
 * @param onlySuggestions: If true, the user can only select from the suggestions.
 * @param initialValues: Initial selected tags.
 * @param withQuantity: If true, allows the user to specify a quantity for each tag.
 * @param suggestionsAlwaysVisible: If true, the suggestions will always be visible. Note that all suggestions will be shown, provided the input field is empty.
 * Otherwise, the suggestions will be filtered according to the input text.
 * @param onTagsChanged: Callback that is called when the selected tags change.
 * @param shape: Shape of the input field. Applies to the tag items as well.
 * @param tagsItemShape: Shape of the tag items.
 * @param outlined: If true, the input field will be outlined. Else it's filled. Refer to [KwikOutlinedTextField] and [KwikTextField].
 * @param tagsVerticalSpacing: Vertical spacing between tags.
 * @param tagsHorizontalSpacing: Horizontal spacing between tags.
 * @param quantityCancelText: Text for the cancel button in the quantity dialog.
 * @param quantityDoneText: Text for the done button in the quantity dialog.
 * */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KwikTagsInput(
    items: List<KwikTagsInputItem> = emptyList(),
    placeholder: String = "",
    onlySuggestions: Boolean = false,
    initialValues: List<KwikTagsInputItem> = emptyList(),
    withQuantity: Boolean = false,
    suggestionsAlwaysVisible: Boolean = false,
    onTagsChanged: (List<KwikTagsInputItem>) -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    tagsItemShape: Shape = MaterialTheme.shapes.small,
    outlined: Boolean = false,
    tagsVerticalSpacing: Int = 4,
    tagsHorizontalSpacing: Int = 4,
    quantityCancelText: String = "Cancel",
    quantityDoneText: String = "Done",
    maxLines: Int = 4
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val inputValue = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val selectedItems = remember { mutableStateMapOf<Any, KwikTagsInputItem>() }
    val suggestions = remember { mutableStateListOf<KwikTagsInputItem>() }
    val showSuggestions = remember { mutableStateOf(false) }
    val kwikToastState = rememberKwikToastState()
    var isFocused by remember { mutableStateOf(false) }

    KwikToast(state = kwikToastState)

    LaunchedEffect(initialValues) {
        if (initialValues.isNotEmpty() && selectedItems.isEmpty()) {
            initialValues.forEach {
                selectedItems[it.id] = it
            }
        }
    }

    fun updateSuggestions() {
        val query = inputValue.value.text
        suggestions.clear()
        suggestions.addAll(
            items.filter { suggestion ->
                suggestion.label.contains(query, ignoreCase = true) && !selectedItems.containsKey(suggestion.id)
            }
        )
        showSuggestions.value = suggestionsAlwaysVisible || inputValue.text.isNotEmpty()
    }

    fun tagAdded(item: KwikTagsInputItem) {
        if (selectedItems.none { it.key == item.id }) {
            selectedItems[item.id] = item
            inputValue.value = TextFieldValue("")
            showSuggestions.value = suggestionsAlwaysVisible
            onTagsChanged(selectedItems.map { it.value })
            updateSuggestions()
        }
    }

    fun tagRemoved(item: KwikTagsInputItem) {
        selectedItems.remove(item.id)
        onTagsChanged(selectedItems.map { it.value })
        updateSuggestions()
    }

    fun updateQuantity(id: Any, quantity: Int) {
        val item = selectedItems[id]
        if (item != null) {
            selectedItems[id] = item.copy(quantity = quantity)
        }
        onTagsChanged(selectedItems.map { it.value })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if(outlined) 2.dp else 0.dp,
                color = if(outlined) {
                    if(isFocused){
                        if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
                    } else {
                        Color.Gray
                    }
                } else Color.Transparent,
                shape = shape
            )
            .background(
                color = if(isFocused) {
                    if(outlined) Color.Transparent else if(isSystemInDarkTheme()) KwikColorFilledTextFieldFocusedDarkMode else KwikColorFilledTextFieldFocused
                } else {
                    if(outlined) Color.Transparent else if(isSystemInDarkTheme()) KwikColorFilledTextFieldUnfocusedDarkMode else KwikColorFilledTextFieldUnfocused
                },
                shape = shape
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (selectedItems.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = tagsVerticalSpacing.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        space = tagsHorizontalSpacing.dp
                    ),
                    maxLines = maxLines
                ) {
                    selectedItems.forEach { item ->
                        KwikTagChip(
                            tag = item.value,
                            withQuantity = withQuantity,
                            shape = tagsItemShape,
                            quantity = item.value.quantity,
                            onQuantityChange = { newQuantity ->
                                updateQuantity(item.key, newQuantity)
                            },
                            onRemove = { tagRemoved(item.value) },
                            quantityCancelText = quantityCancelText,
                            quantityDoneText = quantityDoneText
                        )
                    }
                }
            }

            KwikTextField(
                value = inputValue,
                onValueChange = {
                    inputValue.value = it
                    updateSuggestions()
                },
                placeholder = placeholder,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = kwikTextFieldColors().copy(
                    focusedTextColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedLabelColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (inputValue.text.isNotEmpty()) {
                            if (onlySuggestions) {
                                kwikToastState.showToast("You can only select from the suggestions")
                            } else {
                                tagAdded(KwikTagsInputItem(UUID.randomUUID().toString(), inputValue.text))
                            }
                        }
                        keyboardController?.hide()
                    }
                ),
                onFocusChanged = { focused ->
                    isFocused = focused
                    showSuggestions.value = focused && suggestionsAlwaysVisible
                    if (focused) {
                        updateSuggestions()
                    }
                }
            )

            AnimatedVisibility(
                visible = showSuggestions.value
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 240.dp)
                ) {
                    items(suggestions) { suggestion ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = MaterialTheme.shapes.small
                                )
                                .clickable { tagAdded(suggestion) },
                            color = Color.Transparent
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                KwikText.BodyMedium(
                                    text = suggestion.label
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KwikTagChip(
    tag: KwikTagsInputItem,
    withQuantity: Boolean,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    quantityCancelText: String,
    quantityDoneText: String
) {
    val showQuantityDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .clip(shape)
            .clickable {
                if(withQuantity){
                    showQuantityDialog.value = true
                }
            },
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            KwikText.BodyMedium(
                text = tag.label,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (withQuantity) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    KwikText.BodyMedium(
                        text = quantity.toString(),
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }

            KwikHSpacer(width = 2)

            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = "Remove tag",
                    tint = if(isSystemInDarkTheme()) Color.White else Color.Black
                )
            }
        }
    }

    KwikDialog.ContentDialog(
        open = showQuantityDialog.value,
        dismiss = {
            showQuantityDialog.value = false
        }
    ) {
        KwikQuantitySelection(
            tag = tag,
            quantity = tag.quantity,
            onQuantitySelected = {
                onQuantityChange(it)
            },
            onDismiss = { showQuantityDialog.value = false },
            cancelText = quantityCancelText,
            doneText = quantityDoneText
        )
    }
}

@Composable
private fun KwikQuantitySelection(
    quantity: Int,
    tag: KwikTagsInputItem,
    onQuantitySelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    cancelText: String,
    doneText: String
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Select quantity",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        KwikCounter(
            initialValue = quantity,
            minValue = tag.minQuantity,
            maxValue = tag.maxQuantity,
            onValueChange = {
                onQuantitySelected(it)
            }
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            KwikTextButton(
                text = cancelText,
                onClick = onDismiss,
                modifier = Modifier
            )

            KwikButton(
                text = doneText,
                onClick = onDismiss
            )
        }
    }
}

@Preview
@Composable
private fun PreviewKwikTagsInput() {
    val sampleTags = remember {
        listOf(
            KwikTagsInputItem("1", "Tortuga"),
            KwikTagsInputItem("2", "Shipwreck Cove"),
            KwikTagsInputItem("3", "Davy Jones' Locker"),
            KwikTagsInputItem("4", "Port Royal"),
            KwikTagsInputItem("5", "Isla de Muerta"),
            KwikTagsInputItem("6", "Fountain of Youth")
        )
    }

    val initialTags = remember {
        listOf(sampleTags.first())
    }

    var currentTags by remember { mutableStateOf(initialTags) }

    KwikTagsInput(
        items = sampleTags,
        placeholder = "Enter or select your destination",
        initialValues = initialTags,
        withQuantity = true,
        onTagsChanged = { newTags ->
            currentTags = newTags
        }
    )
}