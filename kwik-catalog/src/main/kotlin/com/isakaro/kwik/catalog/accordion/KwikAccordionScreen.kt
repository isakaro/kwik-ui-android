package com.isakaro.kwik.catalog.accordion

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
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.catalog.ScrollableShowCaseContainer
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.accordion.KwikAccordion
import com.isakaro.kwik.ui.accordion.KwikAccordionGroup
import com.isakaro.kwik.ui.accordion.KwikAccordionItem
import com.isakaro.kwik.ui.text.KwikText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikAccordionScreen(
    navigator: DestinationsNavigator = navigator()
) {

    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }
    var expanded4 by remember { mutableStateOf(false) }
    var expanded5 by remember { mutableStateOf(false) }

    ScrollableShowCaseContainer(
        title = "Accordion",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Kwik Accordion") {
            KwikAccordion(
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
                        KwikText.BodyMedium(text = "Content")
                    }
               }
            }
        }

        ShowCase(title = "Kwik Accordion with custom background color") {
            KwikAccordion(
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
                        KwikText.BodyMedium(text = "Content", color = Color.White)
                    }
                }
            }
        }

        ShowCase(title = "Kwik Accordion with 1.dp elevation") {
            KwikAccordion(
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
                        Text("Content")
                    }
                }
            }
        }

        ShowCase(title = "Kwik Accordion with custom header icon") {
            KwikAccordion(
                title = "This is a title",
                expanded = expanded4,
                onExpandedChange = {
                    expanded4 = it
                },
                headerIcon = R.drawable.qr_code_scanner,
                elevation = 1
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    repeat(5) {
                        KwikText.BodyMedium(text = "Content")
                    }
                }
            }
        }

        ShowCase(title = "Kwik Accordion with error") {
            KwikAccordion(
                title = "This is a title",
                expanded = expanded5,
                onExpandedChange = {
                    expanded5 = it
                },
                isError = true,
                errorIcon = R.drawable.qr_code_scanner,
                elevation = 1
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    repeat(5) {
                        KwikText.BodyMedium(text = "Content")
                    }
                }
            }
        }

        ShowCase(title = "Accordion group") {
            val items = listOf(
                KwikAccordionItem("Tortuga", "A lawless island for pirates to hide and do business"),
                KwikAccordionItem("Isla de Muerta", "Can only be found by those who already know where it is"),
                KwikAccordionItem("Davy Jones' Locker", "You don't want to end up there, trust me"),
            )

            KwikAccordionGroup(
                items = items,
                titleProvider = { it.title },
                elevation = 2,
                errorProvider = { it.hasError },
                content = { item ->
                    KwikText.BodyMedium(text = item.content, modifier = Modifier.padding(16.dp))
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewKwikAccordionScreen() {
    KwikAccordionScreen()
}