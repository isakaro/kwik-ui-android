package com.isakaro.kwik

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TextWithIcon(
    text: String,
    icon: Any,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        if(icon is Int){
            Icon(
                modifier = modifier,
                painter = painterResource(id = icon),
                tint = Color.Gray,
                contentDescription = null
            )
        } else if(icon is ImageVector){
            Icon(
                modifier = modifier,
                imageVector = icon,
                tint = Color.Gray,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.Gray,
            style = MaterialTheme.typography.labelMedium
        )
    }
}