package com.isakaro.qwik

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.UUID

@Composable
fun QwikLazyList(
    state: LazyListState,
    items: List<QwikListItemActionState>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = state
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.hashCode() }
        ) { index, item ->
            when(item){
                is QwikListItemActionState.Space -> {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                is QwikListItemActionState.Header -> {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is QwikListItemActionState.Data -> {
                    QwikListActionItem(
                        item = item.action,
                        isLastItem = index == items.size - 1,
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
fun QwikListActionItem(
    item: QwikListItemAction,
    isLastItem: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        if(item.icon != null){
            QwikImageView(url = item.icon)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = item.tint ?: Color.Black,
            )
            if(item.description.isNotBlank()){
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    color = Color.Gray
                )
            }
        }
    }
    if(!isLastItem) Spacer(modifier = Modifier
        .background(Color.LightGray)
        .fillMaxWidth()
        .height(1.dp))
}

data class QwikListItemAction(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String = "",
    val icon: Any? = null,
    val tint: Color? = null,
    val action: () -> Unit = {}
)

sealed class QwikListItemActionState {
    data class Header(val title: String) : QwikListItemActionState()
    data class Space(val id: UUID = UUID.randomUUID()) : QwikListItemActionState()
    data class Data(val action: QwikListItemAction) : QwikListItemActionState()
}