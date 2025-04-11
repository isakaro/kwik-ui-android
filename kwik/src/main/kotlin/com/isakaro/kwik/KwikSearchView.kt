package com.isakaro.kwik

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.isakaro.kwik.theme.KwikColorFilledTextFieldError
import com.isakaro.kwik.theme.KwikColorFilledTextFieldFocused
import com.isakaro.kwik.theme.KwikColorFilledTextFieldFocusedDarkMode
import com.isakaro.kwik.theme.KwikTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A search view that allows users to search for items.
 * @param modifier Modifier to be applied to the view.
 * @param suggestionsModifier Modifier to be applied to the suggestions view.
 * @param state The state of the search view.
 * @param suggestions The list of suggestions to be displayed.
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
 * @param onSuggestionSelected The callback to be called when a suggestion is selected.
 * @param showSuggestionsOnFocus Whether to show suggestions when the search field is focused or not.
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
 * */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun KwikSearchView(
    modifier: Modifier = Modifier,
    suggestionsModifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    placeholder: String = "Search",
    label: String? = null,
    delay: Boolean = false,
    delayDuration: Long = 500L,
    maxChars: Int = 30,
    isError: Boolean = false,
    error: String? = null,
    onTextChange: (String) -> Unit,
    onTextCleared: () -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
    onSuggestionSelected: (String) -> Unit = {},
    suggestions: List<String> = listOf(),
    showSuggestionsOnFocus: Boolean = true,
    suggestionsContainerColor: Color = MaterialTheme.colorScheme.surface
) {
    val queryText = remember { mutableStateOf("") }
    var suggestionsVisible by remember { mutableStateOf(showSuggestionsOnFocus) }
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    var filteredSuggestions by remember { mutableStateOf(suggestions.take(10)) }
    var textFieldPosition by remember { mutableStateOf<Rect?>(null) }
    var textFieldSize by remember { mutableStateOf<IntSize?>(null) }

    Column {
        if(!label.isNullOrBlank()){
            KwikText.BodyMedium(
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
                    state.value = TextFieldValue("")
                    suggestionsVisible = false
                    onTextCleared()
                    return@TextField
                }
                if (query.text.length <= maxChars) {
                    state.value = query
                    queryText.value = query.text
                    suggestionsVisible = suggestions.isNotEmpty()

                    debounceJob?.cancel()
                    debounceJob = coroutineScope.launch {
                        if(delay && delayDuration >= 1L) {
                            delay(delayDuration)
                        }

                        // filter suggestions based on the query
                        filteredSuggestions = suggestions.filter { suggestion ->
                            suggestion.contains(query.text, ignoreCase = true)
                        }

                        // if no results, show all suggestions
                        if (filteredSuggestions.isEmpty()) {
                            filteredSuggestions = suggestions.take(10)
                        }

                        onTextChange(queryText.value)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .then(modifier)
                .onFocusChanged { focusState ->
                    if(showSuggestionsOnFocus && focusState.isFocused){
                        suggestionsVisible = focusState.isFocused
                    }
                    onFocusChanged(focusState.isFocused)
                }.onGloballyPositioned { layoutCoordinates ->
                    textFieldPosition = layoutCoordinates.boundsInWindow()
                    textFieldSize = layoutCoordinates.size
                },
            textStyle = TextStyle(fontSize = 20.sp),
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
                                state.value = TextFieldValue("")
                                queryText.value = ""
                                suggestionsVisible = false
                                debounceJob?.cancel()
                                onTextCleared()
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
                    color = if(isError) MaterialTheme.colorScheme.error else Color.Gray,
                    textAlign = TextAlign.Start
                )
            },
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = if(isSystemInDarkTheme()) KwikColorFilledTextFieldFocusedDarkMode else KwikColorFilledTextFieldFocused,
                focusedLabelColor = Color.Gray,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = if(isSystemInDarkTheme()) KwikColorFilledTextFieldFocusedDarkMode else KwikColorFilledTextFieldFocused,
                unfocusedLabelColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = Color.Transparent,
                disabledContainerColor = Color.LightGray,
                errorContainerColor = KwikColorFilledTextFieldError,
                errorBorderColor = Color.Transparent,
                errorLabelColor = MaterialTheme.colorScheme.error,
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
                errorTextColor = MaterialTheme.colorScheme.error,
                errorCursorColor = MaterialTheme.colorScheme.error
            ),
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
    if(suggestions.isNotEmpty() && textFieldPosition != null){
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
                x = textFieldPosition!!.width.toInt(),
                y = textFieldPosition!!.height.toInt() * 2 - textFieldSize!!.height / 3
            )
        ) {
            AnimatedVisibility(
                visible = suggestionsVisible,
                enter = slideInVertically { -it },
                exit = slideOutVertically { -it }
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
                                        state.value = TextFieldValue(suggestion)
                                        queryText.value = suggestion
                                        suggestionsVisible = false
                                        onSuggestionSelected(suggestion)
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

@Preview
@Composable
private fun KwikSearchViewPreview() {
    KwikTheme {
        KwikSearchView(
            state = remember { mutableStateOf(TextFieldValue("")) },
            onTextChange = {},
            onTextCleared = {}
        )
    }
}

@Preview
@Composable
private fun KwikSearchViewWithErrorPreview() {
    KwikTheme {
        KwikSearchView(
            state = remember { mutableStateOf(TextFieldValue("")) },
            onTextChange = {},
            isError = true,
            error = "This field is required",
            onTextCleared = {}
        )
    }
}

