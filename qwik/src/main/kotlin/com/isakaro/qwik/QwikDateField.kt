package com.isakaro.qwik

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.R
import com.isakaro.qwik.components.IsakaroText

@Composable
fun QwikDateField(
    modifier: Modifier = Modifier,
    label: String,
    date: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        Text(
            text = label,
            color = Color.Gray,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            modifier = modifier
                .height(50.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
            ),
            border = BorderStroke(2.dp, Color.DarkGray),
            contentPadding = PaddingValues(4.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar),
                    tint = Color.DarkGray,
                    contentDescription = null,
                )

                IsakaroText.BodyText(
                    modifier = Modifier.align(Alignment.Center),
                    text = date,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.arrow_down),
                    tint = Color.DarkGray,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun QwikDateFieldPreview() {
    QwikDateField(
        label = "Check-in",
        date = "2022-12-31",
        onClick = {}
    )
}