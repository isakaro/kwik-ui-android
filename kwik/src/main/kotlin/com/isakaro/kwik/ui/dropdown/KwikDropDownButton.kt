package com.isakaro.kwik.ui.dropdown

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.R
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.text.KwikText

/**
 * A component that displays a dropdown view with a label and a text.
 *
 * @param modifier: Modifier applies to the dropdown view
 * @param label: shows the label of the dropdown
 * @param text: displays the text of the dropdown
 * @param leadingIcon: the icon to display at the start of the dropdown
 * @param onClick: the action to perform when the dropdown is clicked
 *
 * Example usage:
 *
 * ```
 * KwikDropDownButton(
 *         label = "Language",
 *         leadingIcon = R.drawable.shield,
 *         text = "Kinyarwanda",
 *         onClick = {}
 *     )
 * ```
 * */
@Composable
fun KwikDropDownButton(
    modifier: Modifier = Modifier,
    label: String = "",
    text: String,
    leadingIcon: Any? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        if(label.isBlank().not()){
            KwikText.TitleSmall(
                text = label,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            modifier = modifier
                .height(50.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
            ),
            border = BorderStroke(2.dp, Color.Gray),
            contentPadding = PaddingValues(4.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                if(leadingIcon != null){
                    KwikImageView(
                        url = leadingIcon,
                        tint = Color.DarkGray,
                    )
                }

                KwikText.TitleSmall(
                    modifier = Modifier.align(Alignment.Center),
                    text = text
                )

                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    tint = Color.DarkGray,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun KwikDropDownButtonPreview() {
    KwikDropDownButton(
        label = "Language",
        leadingIcon = Icons.Default.KeyboardArrowDown,
        text = "Kinyarwanda",
        onClick = {}
    )
}