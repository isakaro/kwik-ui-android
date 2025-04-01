package com.isakaro.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableText(
    text: String,
    title: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 4
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showReadMore by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.clickable {
            isExpanded = true
        }
    ) {
        Column {
            Text(
                text = text,
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines,
                onTextLayout = { textLayoutResult ->
                    showReadMore = textLayoutResult.hasVisualOverflow
                }
            )

            Spacer(Modifier.height(8.dp))

            if (showReadMore && !isExpanded) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { isExpanded = true }
                    ) {
                        Text(
                            text = "Read More",
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }
        }

        IsakaroDialog.ContentDialog(
            open = isExpanded,
            title = title,
            dismiss = { isExpanded = false },
            cancellable = true,
            content = {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = text,
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { isExpanded = false }){
                            Text(text = "Close")
                        }
                    }
                }
            }
        )
    }
}
