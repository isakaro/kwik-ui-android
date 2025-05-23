package com.isakaro.kwik.ui.inputfields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.isakaro.kwik.ui.text.KwikText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A search view that allows users to search for items.
 * @param modifier Modifier to be applied to the view.
 * @param state The state of the search view.
 * @param placeholder The placeholder text to be displayed.
 * @param delay Whether to delay the search or not. Useful for debouncing.
 * @param delayDuration The duration of the delay in milliseconds.
 * @param maxChars The maximum number of characters allowed in the search field.
 * @param isError Whether the search field has an error or not.
 * @param error The error message to be displayed.
 * @param onTextChange The callback to be called when the text changes.
 * @param onTextCleared The callback to be called when the text is cleared.
 * @param onFocusChanged The callback to be called when the focus changes.
 * @param onKeyboardDone The callback to be called when the keyboard is done.
 * @param suggestionsModifier Modifier to be applied to the suggestions view.
 * @param onSuggestionSelected The callback to be called when a suggestion is selected.
 * @param suggestions The list of suggestions to be displayed.
 * @param suggestionsContainerColor The color of the suggestions container.
 * @param colors The colors to be used for the text field.
 *
 * Example usage:
 *
 * ```
 * KwikSearchView(
 *    state = searchQueryState,
 *    onTextChange = {
 *      // handle text change
 *    },
 *    onFocusChanged = { isFocused ->
 *      // handle focus change
 *    },
 *    onKeyboardDone = {
 *      // validate search query and perform search
 *    },
 *    onSuggestionSelected = { suggestion ->
 *      // handle suggestion selection
 *    }
 *)
 * ```
 *
 * This component is a custom search view that allows users to enter a search query and provides suggestions based on the input. It also handles focus changes, keyboard actions, and error states.
 * ```
 * KwikSearchView(
 *    state = searchQueryState,
 *    suggestions = listOf("Tortuga", "Isla de Muerta", "Shipwreck Cove", "Davy Jones' Locker"),
 *    onTextChange = {
 *      // handle text change
 *    },
 *    onKeyboardDone = {
 *      // validate search query and perform search
 *    },
 *    onSuggestionSelected = { suggestion ->
 *      // handle suggestion selection
 *    }
 *)
 * ```
 * */
@Composable
fun KwikSearchView(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    placeholder: String = "Search",
    label: String? = null,
    delay: Boolean = false,
    delayDuration: Long = 500L,
    maxChars: Int = 30,
    isError: Boolean = false,
    error: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onTextChange: (String) -> Unit,
    onTextCleared: () -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
    suggestionsModifier: Modifier = Modifier,
    onSuggestionSelected: (String) -> Unit = {},
    suggestions: List<String> = listOf(),
    suggestionsContainerColor: Color = kwikFilledColorResolver(),
    colors: TextFieldColors = kwikTextFieldColors().copy(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    ),
    shape: Shape = MaterialTheme.shapes.medium
) {
    val queryText = remember { mutableStateOf("") }
    var suggestionsVisible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    var filteredSuggestions by remember { mutableStateOf(suggestions.take(10)) }
    var textFieldPosition by remember { mutableStateOf<Offset?>(null) }
    var textFieldSize by remember { mutableStateOf<IntSize?>(null) }
    var lastInputType by remember { mutableStateOf(LastInputType.TYPING) }

    fun updateSuggestions(suggestion: String? = null){
        filteredSuggestions = filteredSuggestions.filter { it.lowercase() != (suggestion ?: queryText.value).lowercase() }
        lastInputType = LastInputType.SUGGESTION
        suggestionsVisible = filteredSuggestions.isNotEmpty()
    }

    fun handleTextCleared(){
        state.value = TextFieldValue("")
        debounceJob?.cancel()
        onTextCleared()
        filteredSuggestions = suggestions.take(10)
        suggestionsVisible = true
    }

    Column {
        if(!label.isNullOrBlank()){
            KwikText.TitleMedium(
                modifier = Modifier.padding(bottom = 4.dp),
                text = label,
                color = if(isSystemInDarkTheme()) Color.Gray else Color.DarkGray,
                textAlign = TextAlign.Start
            )
        }

        TextField(
            value = state.value,
            onValueChange = { query ->
                if (query.text.isBlank()) {
                    handleTextCleared()
                    return@TextField
                } else if (query.text.length <= maxChars) {
                    state.value = query
                    queryText.value = query.text

                    debounceJob?.cancel()
                    debounceJob = coroutineScope.launch {
                        if(delay && delayDuration >= 1L) {
                            delay(delayDuration)
                        }

                        // filter suggestions based on the query
                        filteredSuggestions = suggestions.filter { suggestion ->
                            suggestion.contains(query.text, ignoreCase = true)
                        }

                        if(lastInputType == LastInputType.TYPING){
                            updateSuggestions()
                        } else {
                            lastInputType = LastInputType.TYPING
                        }

                        onTextChange(queryText.value)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(45.dp)
                .then(modifier)
                .onFocusChanged { focusState ->
                    suggestionsVisible = focusState.isFocused
                    if(focusState.isFocused){
                        updateSuggestions()
                    }
                    onFocusChanged(focusState.isFocused)
                }.onGloballyPositioned { layoutCoordinates ->
                    textFieldPosition = layoutCoordinates.positionInParent()
                    textFieldSize = layoutCoordinates.size
                },
            textStyle = textStyle,
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    contentDescription = "search",
                    modifier = Modifier.size(26.dp)
                )
            },
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.width(90.dp).padding(end = 12.dp).fillMaxHeight()
                ) {
                    if (state.value.text.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                handleTextCleared()
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                                contentDescription = "clear all",
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                    if(isError) {
                        Icon(
                            imageVector = Icons.Filled.Info, "field error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            placeholder = {
                KwikText.TitleMedium(
                    text = placeholder,
                    color = if (isError) MaterialTheme.colorScheme.error else Color.Gray,
                    textAlign = TextAlign.Start
                )
            },
            singleLine = true,
            isError = isError,
            shape = shape,
            colors = colors,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
            ),
            keyboardActions = KeyboardActions(onDone = { onKeyboardDone() })
        )
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            if(isError && error != null){
                KwikText.LabelMedium(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
    if(filteredSuggestions.isNotEmpty() && textFieldPosition != null){
        Popup(
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                suggestionsVisible = false
            },
            offset = IntOffset(
                x = textFieldPosition!!.x.toInt(),
                y = (textFieldPosition!!.y + (textFieldSize!!.height * 1.6)).toInt()
            )
        ) {
            AnimatedVisibility(
                visible = suggestionsVisible,
                enter = fadeIn() + slideInVertically { -it },
                exit = fadeOut() + slideOutVertically { -it }
            ) {
                Column(
                    modifier = suggestionsModifier
                        .fillMaxWidth()
                        .background(
                            color = suggestionsContainerColor,
                            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        filteredSuggestions.forEach { suggestion ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(horizontal = 4.dp)
                                    .clickable {
                                        lastInputType = LastInputType.SUGGESTION
                                        state.value = state.value.copy(text = suggestion, selection = TextRange(suggestion.length))
                                        queryText.value = suggestion
                                        onSuggestionSelected(suggestion)
                                        updateSuggestions(suggestion)
                                        suggestionsVisible = false
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                KwikText.BodyMedium(
                                    text = suggestion
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

internal enum class LastInputType {
    SUGGESTION,
    TYPING
}

@Preview
@Composable
private fun KwikSearchViewPreview() {
    KwikSearchView(
        modifier = Modifier.height(55.dp),
        state = remember { mutableStateOf(TextFieldValue("")) },
        onTextChange = {},
        onTextCleared = {}
    )
}

@Preview
@Composable
private fun KwikSearchViewWithErrorPreview() {
    KwikSearchView(
        modifier = Modifier.height(55.dp),
        state = remember { mutableStateOf(TextFieldValue("")) },
        onTextChange = {},
        isError = true,
        error = "This field is required",
        onTextCleared = {}
    )
}

