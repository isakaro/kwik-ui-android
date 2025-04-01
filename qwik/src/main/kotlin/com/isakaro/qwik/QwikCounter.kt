package com.isakaro.qwik

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
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
import com.isakaro.qwik.R

@Composable
fun IsakaroCounter(
    modifier: Modifier = Modifier,
    label: String = "",
    initialValue: Int = 0,
    minValue: Int = 0,
    maxValue: Int = 99,
    disabled: Boolean = false,
    withBorder: Boolean = false,
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
        if (label.isNotEmpty()) {
            Text(
                text = label,
                color = Color.Gray,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.small
                ).border(
                    width = if(withBorder) 2.dp else 0.dp,
                    color = Color.Gray,
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
                modifier = Modifier.height(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.remove),
                    contentDescription = "Decrement"
                )
            }

            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleMedium,
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
                modifier = Modifier.height(40.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Increment"
                )
            }
        }
    }
}

@Preview
@Composable
fun IsakaroCounterPreview() {
    IsakaroCounter(
        label = "Counter",
        initialValue = 2,
        onValueChange = {}
    )
}