package com.isakaro.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.R

@Composable
fun BackButton(onClick: () -> Unit = {}){
    Button(
        onClick = { onClick() },
        modifier = Modifier.padding(10.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Transparent),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            tint = Color.Black,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun BackButtonPreview(){
    BackButton()
}