package com.isakaro.qwik.catalog.accordion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.QwikAccordion
import com.isakaro.qwik.QwikButton
import com.isakaro.qwik.catalog.R
import com.isakaro.qwik.catalog.ScrollableShowCaseContainer
import com.isakaro.qwik.catalog.ShowCase

@Composable
internal fun QwikAccordionScreen() {

    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }
    var expanded4 by remember { mutableStateOf(false) }
    var expanded5 by remember { mutableStateOf(false) }

    ScrollableShowCaseContainer {
        ShowCase(title = "Qwik Accordion") {
            QwikAccordion(
                title = "This is a title",
                expanded = expanded,
                onExpandedChange = {
                    expanded = it
                }
            ) {
               Column(
                     modifier = Modifier.padding(8.dp)
               ) {
                    repeat(5) {
                        Text("Content", color = Color.Black)
                    }
               }
            }
        }

        ShowCase(title = "Qwik Accordion with custom background color") {
            QwikAccordion(
                title = "This is a title",
                containerColor = Color.Black,
                headerTextColor = Color.White,
                expanded = expanded2,
                onExpandedChange = {
                    expanded2 = it
                }
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    repeat(5) {
                        Text("Content", color = Color.Black)
                    }
                }
            }
        }

        ShowCase(title = "Qwik Accordion with 1.dp elevation") {
            QwikAccordion(
                title = "This is a title",
                expanded = expanded3,
                onExpandedChange = {
                    expanded3 = it
                },
                elevation = 1
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    repeat(5) {
                        Text("Content", color = Color.Black)
                    }
                }
            }
        }

        ShowCase(title = "Qwik Accordion with custom header icon") {
            QwikAccordion(
                title = "This is a title",
                expanded = expanded4,
                onExpandedChange = {
                    expanded4 = it
                },
                headerIcon = com.isakaro.qwik.R.drawable.shield,
                elevation = 1
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    repeat(5) {
                        Text("Content", color = Color.Black)
                    }
                }
            }
        }

        ShowCase(title = "Qwik Accordion with error") {
            QwikAccordion(
                title = "This is a title",
                expanded = expanded5,
                onExpandedChange = {
                    expanded5 = it
                },
                isError = true,
                errorIcon = com.isakaro.qwik.R.drawable.shield,
                elevation = 1
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    repeat(5) {
                        Text("Content", color = Color.Black)
                    }
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