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
import androidx.compose.ui.res.stringResource
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

import com.isakaro.qwik.theme.ColorSecondaryAccent
import com.isakaro.qwik.theme.ErrorColor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QwikSearchView(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    suggestions: List<String> = listOf(),
    placeholder: String = stringResource(id = R.string.search),
    delay: Boolean = false,
    maxChars: Int = 30,
    isError: Boolean = false,
    error: String? = null,
    onTextChange: (String) -> Unit,
    onTextCleared: () -> Unit,
    onFocusChanged: (Boolean) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
    onSuggestionSelected: (String) -> Unit = {}
) {
    val queryText = remember { mutableStateOf("") }
    val suggestionsVisible = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    Column(
        modifier = Modifier.padding(12.dp)
    ) {
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
                    contentDescription = stringResource(id = R.string.search),
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
                                contentDescription = stringResource(id = R.string.clear_all),
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                    if(isError) {
                        Icon(
                            imageVector = Icons.Filled.Info, "field error",
                            tint = ErrorColor
                        )
                    }
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = ColorSecondaryAccent,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            },
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
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
                    color = ErrorColor,
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
    IsakaroSearchView(
        state = remember { mutableStateOf(TextFieldValue("")) },
        onTextChange = {},
        onTextCleared = {}
    )
}

@Preview
@Composable
fun QwikSearchViewWithErrorPreview() {
    IsakaroSearchView(
        state = remember { mutableStateOf(TextFieldValue("")) },
        onTextChange = {},
        isError = true,
        error = "This field is required",
        onTextCleared = {}
    )
}

