package com.isakaro.kwik.catalog.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.card.KwikCard
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.catalog.utils.KwikConstants
import com.isakaro.kwik.image.KwikImageView
import com.isakaro.kwik.navigator
import com.isakaro.kwik.spacer.KwikVSpacer
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.theme.KwikTheme
import com.isakaro.kwik.timeline.KwikTimelineEntry
import com.isakaro.kwik.timeline.KwikVerticalTimeline
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikTimelineScreen(
    navigator: DestinationsNavigator = navigator()
) {

    var selectedMovieIndex by remember { mutableIntStateOf(0) }

    val timelineEntries = listOf(
        KwikTimelineEntry(
            id = 0,
            title = "The Curse of the Black Pearl (2003)",
            description = "Captain Jack Sparrow teams up with Will Turner to save Elizabeth Swann from cursed pirates.",
            onClick = {

            },
            content = {
                KwikCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column {
                        KwikImageView(
                            url = KwikConstants.SAMPLE_IMAGE
                        )

                        Column(modifier = Modifier.padding(12.dp)) {
                            KwikText.TitleSmall(
                                text = "This is the day you will always remember as the day you almost caught Captain Jack Sparrow!",
                            )
                            KwikVSpacer(height = 8)
                            KwikText.TitleSmall(
                                text = "Captain Barbossa and his crew are cursed to live as the undead, and need the medallion and blood to break the curse.",
                            )
                        }
                    }
                }
            }
        ),
        KwikTimelineEntry(
            id = 1,
            title = "Dead Man's Chest (2006)",
            description = "Jack Sparrow races to recover the heart of Davy Jones to avoid enslaving his soul to Jones' service.",
            onClick = { selectedMovieIndex = 1 },
            content = {
                KwikCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column {
                        KwikImageView(
                            url = KwikConstants.SAMPLE_IMAGE
                        )

                        Column(modifier = Modifier.padding(12.dp)) {
                            KwikText.TitleSmall(
                                text = "I've got a jar of dirt! I've got a jar of dirt!"
                            )
                            KwikVSpacer(height = 8)
                            KwikText.TitleSmall(
                                text = "Jack must find the heart of Davy Jones while the East India Trading Company seeks to control the seas."
                            )
                        }
                    }
                }
            }
        ),
        KwikTimelineEntry(
            id = 2,
            title = "At World's End (2007)",
            description = "Jack, Barbossa, Will, Elizabeth, and the crew must sail to the edge of the world to rescue Jack from Davy Jones' Locker.",
            onClick = { selectedMovieIndex = 2 },
            content = {
                KwikCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column {
                        KwikImageView(
                            url = KwikConstants.SAMPLE_IMAGE
                        )

                        Column(modifier = Modifier.padding(12.dp)) {
                            KwikText.TitleSmall(
                                text = "The Brethren Court is called. And you and I must answer."
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
            onClick = { selectedMovieIndex = 3 },
            content = {
                KwikCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column {
                        KwikImageView(
                            url = KwikConstants.SAMPLE_IMAGE
                        )

                        Column(modifier = Modifier.padding(12.dp)) {
                            KwikText.TitleSmall(
                                text = "Did everyone see that? Because I will not be doing it again."
                            )
                            KwikVSpacer(height = 8)
                            KwikText.TitleSmall(
                                text = "Jack encounters mermaids, zombies, and Blackbeard in his quest for the Fountain of Youth."
                            )
                        }
                    }
                }
            }
        ),
        KwikTimelineEntry(
            id = 4,
            title = "Dead Men Tell No Tales (2017)",
            description = "Captain Jack Sparrow searches for the Trident of Poseidon while being pursued by an old rival, Captain Salazar.",
            onClick = { selectedMovieIndex = 4 },
            content = {
                KwikCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column {
                        KwikImageView(
                            url = KwikConstants.SAMPLE_IMAGE
                        )

                        Column(modifier = Modifier.padding(12.dp)) {
                            KwikText.TitleSmall(
                                text = "A pirate's life, Henry... it's the only life."
                            )
                            KwikVSpacer(height = 8)
                            KwikText.TitleSmall(
                                text = "Jack teams up with Henry Turner and astronomer Carina Smyth to find the Trident and break all curses of the sea."
                            )
                        }
                    }
                }
            }
        )
    )

    ShowCaseContainer(
        title = "Timeline",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        KwikVerticalTimeline(
            entries = timelineEntries,
            highlightCurrentEntry = true,
            clickable = true,
            currentEntryIndex = selectedMovieIndex,
            modifier = Modifier.padding(6.dp),
            onClick = {
                selectedMovieIndex = it
            }
        )
    }
}

