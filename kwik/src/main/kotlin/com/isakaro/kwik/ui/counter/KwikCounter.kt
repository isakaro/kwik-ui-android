package com.isakaro.kwik.ui.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.R
import com.isakaro.kwik.ui.text.KwikText

/**
 * A counter component that allows the user to increment or decrement a value.
 *
 * @param label: The label of the counter.
 * @param initialValue: The initial value of the counter. Default is 0.
 * @param minValue: The minimum value of the counter. Default is 0.
 * @param maxValue: The maximum value of the counter. Default is 99.
 * @param disabled: If true, the counter will be disabled.
 * @param borderColor: The color of the border.
 * @param borderStroke: The width of the border.
 * @param onValueChange: The callback that will be called when the value of the counter changes.
 *
 * Example usage:
 *
 * ```
 * KwikCounter(
 *    label = "Counter",
 *    initialValue = 2,
 *    minValue = 0,
 *    maxValue = 10,
 *    onValueChange = {
 *      // handle value change
 *    }
 *)
 *```
 * */
@Composable
fun KwikCounter(
    modifier: Modifier = Modifier,
    label: String? = null,
    initialValue: Int = 0,
    minValue: Int = 0,
    maxValue: Int = 99,
    disabled: Boolean = false,
    borderColor: Color = Color.Gray,
    borderStroke: Int = 0,
    onValueChange: (Int) -> Unit
) {
    var value by remember { mutableIntStateOf(initialValue) }
    val decrementDisabled = value - 1 < minValue
    val incrementDisabled = value + 1 > maxValue

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .alpha(if (disabled) 0.5f else 1.0f),
    ) {
        if (label != null) {
            KwikText.TitleSmall(
                text = label,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.small
                ).border(
                    width = borderStroke.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(6.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton (
                onClick = {
                    if (!decrementDisabled) {
                        value--
                        onValueChange(value)
                    }
                },
                enabled = !decrementDisabled && !disabled,
                modifier = Modifier.height(40.dp).alpha(if (decrementDisabled) 0.3f else 1.0f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.remove),
                    tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    contentDescription = "Decrement"
                )
            }

            KwikText.TitleMedium(
                text = value.toString(),
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 1
            )

            IconButton(
                onClick = {
                    if (!incrementDisabled) {
                        value++
                        onValueChange(value)
                    }
                },
                enabled = !incrementDisabled && !disabled,
                modifier = Modifier.height(40.dp).alpha(if (incrementDisabled) 0.3f else 1.0f),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    contentDescription = "Increment"
                )
            }
        }
    }
}

@Preview
@Composable
private fun KwikCounterPreview() {
    KwikCounter(
        label = "Counter",
        initialValue = 2,
        onValueChange = {}
    )
}