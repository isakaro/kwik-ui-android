package com.isakaro.qwik.catalog.accordion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.QwikAccordion
import com.isakaro.qwik.QwikButton
import com.isakaro.qwik.catalog.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.ShowCase

@Composable
internal fun QwikAccordionScreen() {

    val expanded by remember { mutableStateOf(false) }

    ScrollableShowCaseContainer {
        ShowCase(title = "Qwik Accordion") {
            QwikAccordion(
                title = "This is a title",
                expanded = expanded,
                onExpandedChange = {}
            ) {
               Column(
                     modifier = Modifier.padding(8.dp)
               ) {
                    repeat(5) {
                        Text("Content", color = Color.Black)
                    }
                   QwikButton(text = "Button")
               }
            }
        }
    }
}

@Preview
@Composable
fun PreviewQwikAccordionScreen() {
    QwikAccordionScreen()
}