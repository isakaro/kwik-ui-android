package com.isakaro.qwik

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Accordion component that can be expanded or collapsed.
 * @param title: The title of the accordion.
 * @param headerIcon: The icon to display on the left side of the title.
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
 * QwikAccordion(
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
fun QwikAccordion(
    title: String,
    headerIcon: Int? = null,
    elevation: Int = 8,
    expanded: Boolean,
    headerTextColor: Color = Color.Black,
    isError: Boolean = false,
    @DrawableRes errorIcon: Int? = null,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation.dp
        )
    ){
        QwikAccordionHeader(
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
private fun QwikAccordionHeader(
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
                    tint = Color.Black,
                    contentDescription = if(isExpanded) "collapse" else "expand",
                )
            }
            Text(
                modifier = Modifier,
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                color = headerTextColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.arrow_down),
                tint = Color.Black,
                contentDescription = if(isExpanded) "collapse" else "expand",
                modifier = Modifier.rotate(rotation),
            )
        }
    }
}