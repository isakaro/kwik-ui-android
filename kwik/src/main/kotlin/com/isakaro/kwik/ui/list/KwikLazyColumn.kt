package com.isakaro.kwik.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.text.KwikText
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import java.util.UUID

/**
 * A handy composable that displays a list of items with actions.
 *
 * @param state The state of the lazy list.
 * @param items The list of items to display.
 * @param showDivider Whether to show a divider between items.
 *
 * Example usage:
 *
 * ```
 * val state = rememberLazyListState()
 *
 * KwikLazyList(
 *    state = state,
 *    items = listOf(
 *      KwikListItemActionState.Header("Header"),
 *      KwikListItemActionState.Data(KwikListItemAction(title = "Item 1", description = "Description 1")),
 *      KwikListItemActionState.Data(KwikListItemAction(title = "Item 2", description = "Description 2")),
 *      KwikListItemActionState.Data(KwikListItemAction(title = "Item 3", description = "Description 3")),
 *      KwikListItemActionState.Space(),
 *      KwikListItemActionState.Header("Header 2"),
 *      KwikListItemActionState.Data(KwikListItemAction(title = "Item 4", description = "Description 4")),
 *      KwikListItemActionState.Data(KwikListItemAction(title = "Item 5", description = "Description 5")),
 *      KwikListItemActionState.Data(KwikListItemAction(title = "Item 6", description = "Description 6")),
 *    )
 *)
 * ```
 *
 * @see KwikListItemAction
 * @see KwikListItemActionState
 * */
@Composable
fun KwikLazyList(
    modifier: Modifier = Modifier,
    state: LazyListState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    items: List<KwikListItemActionState>,
    showDivider: Boolean = true
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier,
        state = state
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.hashCode() }
        ) { index, item ->
            when(item){
                is KwikListItemActionState.Space -> {
                    KwikVSpacer(16)
                }
                is KwikListItemActionState.Header -> {
                    KwikText.TitleMedium(
                        text = item.title
                    )
                }
                is KwikListItemActionState.Data -> {
                    KwikListActionItem(
                        item = item.action,
                        isLastItem = index == items.size - 1,
                        showDivider = showDivider,
                        onClick = {
                            item.action.action()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun KwikListActionItem(
    item: KwikListItemAction,
    isLastItem: Boolean = false,
    showDivider: Boolean = true,
    onClick: () -> Unit
) {
    KwikVSpacer(8)

    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        if(item.icon != null){
            KwikImageView(url = item.icon)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            KwikText.TitleMedium(
                text = item.title,
                color = item.tint ?: MaterialTheme.colorScheme.onSurface,
            )
            if(item.description.isNotBlank()){
                KwikText.BodyMedium(
                    text = item.description,
                    maxLines = 2,
                    color = Color.Gray
                )
            }
        }
    }

    KwikVSpacer(8)

    if(!isLastItem && showDivider) {
        HorizontalDivider()
    }
}

/**
 * The data class representing a list item action.
 * */
data class KwikListItemAction(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String = "",
    val icon: Any? = null,
    val tint: Color? = null,
    val action: () -> Unit = {}
)

/**
 * The state of a list item action.
 * */
sealed class KwikListItemActionState {
    data class Header(val title: String) : KwikListItemActionState()
    data class Space(val id: UUID = UUID.randomUUID()) : KwikListItemActionState()
    data class Data(val action: KwikListItemAction) : KwikListItemActionState()
}