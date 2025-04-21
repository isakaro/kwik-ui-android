package com.isakaro.kwik.ui.text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.button.KwikTextButton

/**
 * Text view that can be expanded or collapsed.
 * Initially shows the text with a cutoff of `maxLines` lines.
 *
 * @param modifier The modifier to be applied to the text view.
 * @param text The text to be displayed.
 * @param maxLines The maximum number of lines to show initially.
 * */
@Composable
fun KwikExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = 4,
    color: Color = MaterialTheme.colorScheme.onSurface,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    readMoreText: String = "Read More",
    showLessText: String = "Show Less"
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showReadMore by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        AnimatedContent(
            targetState = isExpanded,
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            },
            label = "ExpandableText"
        ) { targetExpanded ->
            KwikText.RenderText(
                text = text,
                color = color,
                style = style,
                modifier = Modifier.padding(8.dp),
                overflow = if (targetExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                maxLines = if (targetExpanded) Int.MAX_VALUE else maxLines,
                onTextLayout = { textLayoutResult ->
                    if (!targetExpanded) {
                        showReadMore = textLayoutResult.hasVisualOverflow
                    }
                }
            )
        }

        if (showReadMore) {
            Spacer(Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                KwikTextButton(
                    text = if (isExpanded) showLessText else readMoreText,
                    onClick = { isExpanded = !isExpanded }
                )
            }
        }
    }
}

