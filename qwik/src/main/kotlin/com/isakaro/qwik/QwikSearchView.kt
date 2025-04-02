package com.isakaro.qwik

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.isakaro.qwik.theme.Theme.QwikTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A search view that allows users to search for items.
 * @param modifier Modifier to be applied to the view.
 * @param state The state of the search view.
 * @param suggestions The list of suggestions to be displayed.
 * @param placeholder The placeholder text to be displayed.
 * @param delay Whether to delay the search or not.
 * @param maxChars The maximum number of characters allowed in the search field.
 * @param isError Whether the search field has an error or not.
 * @param error The error message to be displayed.
 * @param onTextChange The callback to be called when the text changes.
 * @param onTextCleared The callback to be called when the text is cleared.
 * @param onFocusChanged The callback to be called when the focus changes.
 * @param onKeyboardDone The callback to be called when the keyboard is done.
 * @param onSuggestionSelected The callback to be called when a suggestion is selected.
 *
 * Example usage:
 *
 * ```
 * QwikSearchView(
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
@Composable
fun QwikSearchView(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    suggestions: List<String> = listOf(),
    placeholder: String = "Search",
    delay: Boolean = false,
    maxChars: Int = 30,
    isError: Boolean = false,
    error: String? = null,
    onTextChange: (String) -> Unit,
    onTextCleared: () -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
    onSuggestionSelected: (String) -> Unit = {}
) {
    val queryText = remember { mutableStateOf("") }
    val suggestionsVisible = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    Column {
        OutlinedTextField(
            value = state.value,
            onValueChange = { query ->
                if (query.text.isBlank()) {
                    state.value = TextFieldValue("")
                    suggestionsVisible.value = false
                    onTextCleared()
                    return@OutlinedTextField
                }
                if (query.text.length <= maxChars) {
                    state.value = query
                    queryText.value = query.text
                    suggestionsVisible.value = suggestions.isNotEmpty()

                    debounceJob?.cancel()
                    debounceJob = coroutineScope.launch {
                        if (delay) {
                            delay(500L)
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
                    onFocusChanged(focusState.isFocused)
                },
            textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    tint = Color.DarkGray,
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
                                suggestionsVisible.value = false
                                debounceJob?.cancel()
                                onTextCleared()
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                tint = Color.DarkGray,
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
                Text(
                    text = placeholder,
                    color = Color.LightGray,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            },
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                disabledBorderColor = Color.Black,
                unfocusedContainerColor = Color.White,
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
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .align(Alignment.BottomEnd),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        DropdownMenu(
            expanded = suggestionsVisible.value,
            onDismissRequest = { suggestionsVisible.value = false },
            containerColor = Color.White,
            properties = PopupProperties(
                focusable = false,
            )
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = { Text(suggestion) },
                    onClick = {
                        state.value = TextFieldValue(suggestion)
                        queryText.value = suggestion
                        suggestionsVisible.value = false
                        onSuggestionSelected(suggestion)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun QwikSearchViewPreview() {
    QwikTheme {
        QwikSearchView(
            state = remember { mutableStateOf(TextFieldValue("")) },
            onTextChange = {},
            onTextCleared = {}
        )
    }
}

@Preview
@Composable
fun QwikSearchViewWithErrorPreview() {
    QwikTheme {
        QwikSearchView(
            state = remember { mutableStateOf(TextFieldValue("")) },
            onTextChange = {},
            isError = true,
            error = "This field is required",
            onTextCleared = {}
        )
    }
}

