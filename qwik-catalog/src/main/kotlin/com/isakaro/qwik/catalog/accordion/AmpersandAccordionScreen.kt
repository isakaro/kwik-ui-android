package com.isakaro.qwik.catalog.accordion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.catalog.common.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.common.ShowCase

@Composable
internal fun AmpersandAccordionScreen() {
    ScrollableShowCaseContainer {
        ShowCase(title = "Accordion") {
            AmpersandAccordion(title = "This is a title") {
               Column(
                     modifier = Modifier.padding(8.dp)
               ) {
                    repeat(5) {
                        Text("Content", color = Color.Black)
                    }
                   AmpersandButton(text = "Button")
               }
            }
        }
    }
}