package com.isakaro.kwik.timeline
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.card.KwikCard
import com.isakaro.kwik.spacer.KwikVSpacer
import com.isakaro.kwik.text.KwikText
import com.isakaro.kwik.theme.KwikTheme

/**
 * Data class representing a single entry in the timeline
 *
 * @param id Unique identifier for the timeline entry. Can be any type. Use it to identify the entry.
 * @param title Optional title text for the timeline entry
 * @param description Optional description text for the timeline entry
 * @param onClick Optional callback when the timeline entry is clicked
 * @param content Optional composable content to be displayed within the timeline entry
 */
data class KwikTimelineEntry(
    val id: Any = Any(),
    val title: String? = null,
    val description: String? = null,
    val onClick: (KwikTimelineEntry) -> Unit = {},
    val content: (@Composable () -> Unit)? = null
)

/**
 * A vertical timeline component that displays a series of entries with indicators and connecting lines.
 *
 * @param entries List of timeline entries to display
 * @param modifier Modifier to be applied to the timeline
 * @param accentColor Color of the timeline indicators and lines
 * @param highlightCurrentEntry Whether to highlight the selected entry (if any)
 * @param currentEntryIndex Optional index of the current/selected entry
 */
@Composable
fun KwikVerticalTimeline(
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    entries: List<KwikTimelineEntry>,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    highlightCurrentEntry: Boolean = false,
    currentEntryIndex: Int = -1,
    onClick: (KwikTimelineEntry) -> Unit = {}
) {

    var selectedIndex by remember { mutableIntStateOf(currentEntryIndex) }

    LaunchedEffect(currentEntryIndex) {
        if (currentEntryIndex != -1) {
            selectedIndex = currentEntryIndex
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        itemsIndexed(
            items = entries,
            key = { _, item -> item.hashCode() }
        ) { index, entry ->
            val isCurrentEntry = index == selectedIndex && highlightCurrentEntry
            val isLastEntry = index == entries.lastIndex

            KwikTimelineEntryItem(
                clickable = clickable,
                entry = entry,
                index = index,
                isCurrentEntry = isCurrentEntry,
                isLastEntry = isLastEntry,
                accentColor = accentColor,
                onClick = onClick
            )
        }
    }
}

/**
* Item for the timeline entry
 *
 * @param clickable Whether the entry is clickable
 * @param entry The timeline entry data
 * @param index The index of the entry in the list
 * @param isCurrentEntry Whether this entry is the current/selected entry
 * @param isLastEntry Whether this entry is the last entry in the list
 * @param accentColor The color of the timeline indicators and lines
 * @param onClick Callback when the entry is clicked
* */
@Composable
private fun KwikTimelineEntryItem(
    clickable : Boolean = false,
    entry: KwikTimelineEntry,
    index: Int,
    isCurrentEntry: Boolean,
    isLastEntry: Boolean,
    accentColor: Color,
    onClick: (KwikTimelineEntry) -> Unit = {}
) {
    val indicatorColor by animateColorAsState(
        targetValue = if (isCurrentEntry) accentColor else Color.Gray,
        animationSpec = tween(300),
        label = "indicator color"
    )

    val lineColor by animateColorAsState(
        targetValue = if (isCurrentEntry) accentColor else Color.Gray,
        animationSpec = tween(300),
        label = "line color"
    )

    var contentHeight by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (clickable) {
                    onClick(entry)
                }
            }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val circleSize = 24.dp

                Box(
                    modifier = Modifier
                        .size(circleSize)
                        .clip(CircleShape)
                        .background(indicatorColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                if (!isLastEntry) {
                    Spacer(
                        modifier = Modifier
                            .width(2.dp)
                            .height(with(LocalDensity.current) {
                                (contentHeight.toDp() - circleSize).coerceAtLeast(0.dp)
                            })
                            .background(lineColor)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .onGloballyPositioned {
                    contentHeight = it.size.height
                }
                .weight(1f)
                .then(
                    Modifier.clickable { entry.onClick.invoke(entry) }
                )
        ) {
            entry.title?.let {
                KwikText.TitleMedium(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    color = if (isCurrentEntry) accentColor else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            entry.description?.let {
                KwikText.BodySmall(
                    text = it,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            entry.content?.let {
                it()
            }

            if(!isLastEntry) {
                KwikVSpacer(height = 8)
            }
        }
    }

}

@Preview
@Composable
private fun KwikTimelineExample() {
    val timelineEntries = listOf(
        KwikTimelineEntry(
            title = "Project Started",
            description = "Initial research and planning phase",
            onClick = {

            }
        ),
        KwikTimelineEntry(
            title = "Design Phase",
            description = "Creating wireframes and mockups",
            content = {
                KwikCard(

                ) {
                    KwikText.BodyMedium(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = "User story collection and planning."
                    )
                }
            }
        ),
        KwikTimelineEntry(
            title = "Development",
            description = "Building the core features of the application"
        ),
        KwikTimelineEntry(
            title = "Testing",
            description = "Quality assurance and bug fixing"
        ),
        KwikTimelineEntry(
            title = "Deployment",
            description = "Release to production environment"
        )
    )

    KwikTheme {
        KwikVerticalTimeline(
            entries = timelineEntries,
            highlightCurrentEntry = true,
            currentEntryIndex = 1
        )
    }
}