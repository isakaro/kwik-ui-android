package com.isakaro.kwik.catalog.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.catalog.R
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.ui.card.KwikCard
import com.isakaro.kwik.ui.card.KwikImageCard
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.catalog.utils.KwikConstants
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.theme.KwikColorWarning
import com.isakaro.kwik.ui.timeline.KwikTimelineEntry
import com.isakaro.kwik.ui.timeline.KwikVerticalTimeline
import com.isakaro.kwik.ui.toast.KwikToast
import com.isakaro.kwik.ui.toast.rememberKwikToastState
import com.isakaro.kwik.ui.toast.showToast
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikTimelineScreen(
    navigator: DestinationsNavigator = navigator()
) {

    val kwikToastState = rememberKwikToastState()

    KwikToast(state = kwikToastState)

    val timelineEntries = listOf(
        KwikTimelineEntry(
            id = 0,
            title = "The Curse of the Black Pearl (2003)",
            description = "Captain Jack Sparrow teams up with Will Turner to save Elizabeth Swann from cursed pirates.",
            onClick = {
                kwikToastState.showToast(message = "Clicked on ${it.title}")
            },
            content = {
                KwikImageCard(
                    image = KwikConstants.SAMPLE_IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        KwikText.Quote(
                            text = "This is the day you will always remember as the day you almost caught Captain Jack Sparrow!",
                            fontStyle = FontStyle.Italic
                        )
                        KwikVSpacer(height = 8)
                        KwikText.TitleSmall(
                            text = "Captain Barbossa and his crew are cursed to live as the undead, and need the medallion and blood to break the curse.",
                        )
                    }
                }
            }
        ),
        KwikTimelineEntry(
            id = 1,
            title = "Dead Man's Chest (2006)",
            description = "Jack Sparrow races to recover the heart of Davy Jones to avoid enslaving his soul to Jones' service.",
            onClick = { kwikToastState.showToast(message = "Clicked on ${it.title}") },
            content = {
                KwikImageCard(
                    image = KwikConstants.SAMPLE_IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        KwikText.Quote(
                            text = "I've got a jar of dirt! I've got a jar of dirt!",
                            fontStyle = FontStyle.Italic
                        )
                        KwikVSpacer(height = 8)
                        KwikText.TitleSmall(
                            text = "Jack must find the heart of Davy Jones while the East India Trading Company seeks to control the seas."
                        )
                    }
                }
            }
        ),
        KwikTimelineEntry(
            id = 2,
            title = "At World's End (2007)",
            description = "Jack, Barbossa, Will, Elizabeth, and the crew must sail to the edge of the world to rescue Jack from Davy Jones' Locker.",
            onClick = { kwikToastState.showToast(message = "Clicked on ${it.title}") },
            content = {
                KwikImageCard(
                    image = KwikConstants.SAMPLE_IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column {
                        Column(modifier = Modifier.padding(12.dp)) {
                            KwikText.Quote(
                                text = "The Brethren Court is called. And you and I must answer.",
                                fontStyle = FontStyle.Italic
                            )
                            KwikVSpacer(height = 8)
                            KwikText.TitleSmall(
                                text = "The pirate lords gather to make a final stand against Lord Cutler Beckett and Davy Jones."
                            )
                        }
                    }
                }
            }
        ),
        KwikTimelineEntry(
            id = 3,
            title = "On Stranger Tides (2011)",
            description = "Jack Sparrow crosses paths with Blackbeard and his daughter while searching for the Fountain of Youth.",
            onClick = { kwikToastState.showToast(message = "Clicked on ${it.title}") },
            content = {
                KwikImageCard(
                    image = KwikConstants.SAMPLE_IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        KwikText.Quote(
                            text = "Did everyone see that? Because I will not be doing it again.",
                            fontStyle = FontStyle.Italic
                        )
                        KwikVSpacer(height = 8)
                        KwikText.TitleSmall(
                            text = "Jack encounters mermaids, zombies, and Blackbeard in his quest for the Fountain of Youth."
                        )
                    }
                }
            }
        ),
        KwikTimelineEntry(
            id = 4,
            title = "Dead Men Tell No Tales (2017)",
            description = "Captain Jack Sparrow searches for the Trident of Poseidon while being pursued by an old rival, Captain Salazar.",
            onClick = { kwikToastState.showToast(message = "Clicked on ${it.title}") },
            content = {
                KwikImageCard(
                    image = KwikConstants.SAMPLE_IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        KwikText.Quote(
                            text = "A pirate's life, Henry... it's the only life.",
                            fontStyle = FontStyle.Italic
                        )
                        KwikVSpacer(height = 8)
                        KwikText.TitleSmall(
                            text = "Jack teams up with Henry Turner and astronomer Carina Smyth to find the Trident and break all curses of the sea."
                        )
                    }
                }
            }
        ),
        KwikTimelineEntry(
            id = 5,
            title = "Directed by Gore Verbinski",
            description = "Gore Verbinski directed the first three films in the series, bringing a unique blend of action and humor.",
            onClick = { kwikToastState.showToast(message = "Clicked on ${it.title}") }
        ),
        KwikTimelineEntry(
            id = 6,
            icon = R.drawable.qr_code_scanner,
            content = {
                KwikImageCard(
                    image = KwikConstants.SAMPLE_IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        KwikText.Quote(
                            text = "The sea is a cruel mistress.",
                            fontStyle = FontStyle.Italic
                        )
                        KwikVSpacer(height = 8)
                        KwikText.TitleSmall(
                            text = "The series has become a cultural phenomenon, inspiring rides, merchandise, and more."
                        )
                    }
                }
            },
            onClick = { kwikToastState.showToast(message = "Clicked on ${it.title}") }
        ),
        KwikTimelineEntry(
            id = 7,
            icon = Icons.Default.Settings,
            accentColor = KwikColorWarning,
            onClick = { kwikToastState.showToast(message = "Clicked on ${it.title}") },
            content = {
                KwikCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        KwikText.TitleMedium(
                            text = "Big budget"
                        )
                        KwikVSpacer(height = 8)
                        KwikText.TitleSmall(
                            text = "The series is known for its high production values, elaborate sets, and special effects."
                        )
                    }
                }
            }
        )
    )

    var completedIndex by remember { mutableIntStateOf(timelineEntries.size) }

    ShowCaseContainer(
        title = "Timeline",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        KwikVerticalTimeline(
            entries = timelineEntries,
            clickable = true,
            currentStepIndex = completedIndex,
            modifier = Modifier.padding(6.dp),
            onClick = {
                kwikToastState.showToast(message = "Clicked on ${it.title}")
            }
        )
    }
}

