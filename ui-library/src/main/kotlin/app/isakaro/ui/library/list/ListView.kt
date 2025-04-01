package app.isakaro.ui.library.list

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.isakaro.ui.library.IsakaroImageView
import java.util.UUID

@Composable
fun ListView(
    items: List<ListItemActionState>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.hashCode() }
        ) { index, item ->
            when(item){
                is ListItemActionState.Space -> {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                is ListItemActionState.Header -> {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is ListItemActionState.Data -> {
                    ListActionItem(
                        item = item.accountAction,
                        isLastItem = index == items.size - 1,
                        onClick = {
                            item.accountAction.action()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ListActionItem(
    item: ListItemAction,
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
        IsakaroImageView(url = item.icon)
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

data class ListItemAction(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String = "",
    val icon: Any,
    val tint: Color? = null,
    val action: () -> Unit = {}
)

sealed class ListItemActionState {
    data class Header(val title: String) : ListItemActionState()
    data class Space(val id: UUID = UUID.randomUUID()) : ListItemActionState()
    data class Data(val accountAction: ListItemAction) : ListItemActionState()
}