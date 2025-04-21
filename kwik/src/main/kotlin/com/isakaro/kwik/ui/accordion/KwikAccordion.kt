package com.isakaro.kwik.ui.accordion

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.text.KwikText

/**
 * Accordion component that can be expanded or collapsed.
 * @param title: The title of the accordion.
 * @param headerIcon: The icon to display on the left side of the title.
 * @param containerColor: The color of the accordion container.
 * @param elevation: The elevation of the accordion.
 * @param expanded: The state of the accordion. If true, the content will be visible.
 * @param headerTextColor: The color of the title.
 * @param isError: If true, the accordion will display an error icon.
 * @param errorIcon: The icon to display when isError is true.
 * @param onExpandedChange: The callback that will be called when the accordion is expanded or collapsed.
 * @param content: The content of the accordion.
 *
 * Example usage:
 * ```
 * KwikAccordion(
 *    title = "Accordion",
 *    headerIcon = R.drawable.icon,
 *    expanded = true,
 *    headerTextColor = Color.Black,
 *  ){
 *      Text("Accordion content")
 *  }
 *  ```
 * */
@Composable
@Stable
fun KwikAccordion(
    title: String,
    headerIcon: Int? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    elevation: Int = 8,
    expanded: Boolean,
    headerTextColor: Color = MaterialTheme.colorScheme.onSurface,
    isError: Boolean = false,
    @DrawableRes errorIcon: Int? = null,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation.dp
        )
    ){
        KwikAccordionHeader(
            title = title,
            headerIcon = headerIcon,
            isExpanded = expanded,
            headerTextColor = headerTextColor,
            isError = isError,
            errorIcon = errorIcon,
            onClick = { onExpandedChange(!expanded) }
        )
        AnimatedVisibility(visible = expanded) {
            content()
        }
    }
}

@Composable
private fun KwikAccordionHeader(
    title: String,
    headerIcon: Int? = null,
    isExpanded: Boolean = false,
    headerTextColor: Color,
    isError: Boolean = false,
    errorIcon: Int? = null,
    onClick: () -> Unit = {}
){
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Column {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(isError && errorIcon != null){
                Icon(
                    modifier = Modifier.padding(end = 3.dp),
                    painter = painterResource(errorIcon),
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "error"
                )
            }
            if(headerIcon != null){
                Icon(
                    modifier = Modifier.padding(end = 3.dp),
                    painter = painterResource(id = headerIcon),
                    tint = headerTextColor,
                    contentDescription = if(isExpanded) "collapse" else "expand",
                )
            }
            KwikText.BodyMedium(
                modifier = Modifier,
                text = title,
                fontWeight = FontWeight.Bold,
                color = if (isError) MaterialTheme.colorScheme.error else headerTextColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = headerTextColor,
                contentDescription = if(isExpanded) "collapse" else "expand",
                modifier = Modifier.rotate(rotation),
            )
        }
    }
}

/**
 * Data class representing an item in the accordion.
 * Contains a title, content, and an optional error state.
 *
 * @param title: The title of the accordion item.
 * @param content: The content of the accordion item.
 * @param hasError: If true, the item will display an error state.
* */
data class KwikAccordionItem(val title: String, val content: String, val hasError: Boolean = false)

/**
 * AccordionGroup component that manages a group of accordions.
 * Can be configured to allow multiple accordions to be expanded at once or only one at a time.
 *
 * @param allowMultipleExpanded: If true, multiple accordions can be expanded at once. If false, only one accordion can be expanded at a time.
 * @param initialExpandedIndices: List of indices that should be expanded initially.
 * @param items: List of items to create accordions for.
 * @param titleProvider: Function to extract the title from an item.
 * @param headerIconProvider: Function to extract the header icon from an item (optional).
 * @param containerColor: The color of the accordion containers.
 * @param elevation: The elevation of the accordions.
 * @param headerTextColor: The color of the titles.
 * @param errorProvider: Function to determine if an item should display an error (optional).
 * @param errorIconProvider: Function to extract the error icon from an item (optional).
 * @param contentProvider: Function to provide the content for each accordion.
 *
 * Example usage:
 * ```
 * val items = listOf(
 *    AccordionItem("First Item", "Content for first item"),
 *    AccordionItem("Second Item", "Content for second item", true),
 *    AccordionItem("Third Item", "Content for third item")
 * )
 *
 * KwikAccordionGroup(
 *    allowMultipleExpanded = false,
 *    initialExpandedIndices = listOf(0),
 *    items = items,
 *    titleProvider = { it.title },
 *    containerColor = Color.White,
 *    elevation = 8,
 *    headerTextColor = Color.Black,
 *    errorProvider = { it.hasError },
 *    errorIcon = R.drawable.error_icon
 * ) { item ->
 *    Text(text = item.content, modifier = Modifier.padding(16.dp))
 * }
 * ```
 */
@Composable
fun <T> KwikAccordionGroup(
    allowMultipleExpanded: Boolean = true,
    initialExpandedIndices: List<Int> = emptyList(),
    items: List<T>,
    titleProvider: (T) -> String,
    headerIconProvider: ((T) -> Int?)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    elevation: Int = 8,
    headerTextColor: Color = MaterialTheme.colorScheme.onSurface,
    errorProvider: ((T) -> Boolean)? = null,
    @DrawableRes errorIcon: Int? = null,
    content: @Composable (T) -> Unit
) {
    val expandedItems = remember {
        mutableStateListOf<Int>().apply {
            addAll(initialExpandedIndices)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isExpanded = index in expandedItems
            val isError = errorProvider?.invoke(item) ?: false

            KwikAccordion(
                title = titleProvider(item),
                headerIcon = headerIconProvider?.invoke(item),
                containerColor = containerColor,
                elevation = elevation,
                expanded = isExpanded,
                headerTextColor = headerTextColor,
                isError = isError,
                errorIcon = errorIcon,
                onExpandedChange = { expanded ->
                    if (expanded) {
                        if (!allowMultipleExpanded) {
                            expandedItems.clear()
                        }
                        expandedItems.add(index)
                    } else {
                        expandedItems.remove(index)
                    }
                }
            ) {
                content(item)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewKwikAccordionGroup() {
    val items = listOf(
        KwikAccordionItem("First Item", "Content for first item"),
        KwikAccordionItem("Second Item", "Content for second item", true),
        KwikAccordionItem("Third Item", "Content for third item")
    )

    KwikAccordionGroup(
        items = items,
        titleProvider = { it.title },
        containerColor = Color.White,
        elevation = 2,
        headerTextColor = Color.Black,
        errorProvider = { it.hasError },
        content = { item ->
            Text(text = item.content, modifier = Modifier.padding(16.dp))
        }
    )
}

