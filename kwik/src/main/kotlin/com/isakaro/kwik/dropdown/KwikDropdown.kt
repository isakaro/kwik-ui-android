package com.isakaro.kwik.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.spacer.KwikVSpacer
import com.isakaro.kwik.text.KwikText
import java.util.UUID

@Composable
fun KwikDropdown(
    state: Boolean,
    shape: Shape = MaterialTheme.shapes.large,
    onDismissRequest: () -> Unit,
    items: List<KwikDropdownItemActionState>,
) {
    DropdownMenu(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.surface
        ),
        shape = shape,
        expanded = state,
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            items.forEach { item ->
                when (item) {
                    is KwikDropdownItemActionState.Header -> {
                        KwikText.TitleMedium(text = item.title)
                    }
                    is KwikDropdownItemActionState.Space -> {
                        KwikVSpacer(8)
                    }
                    is KwikDropdownItemActionState.Divider -> {
                        HorizontalDivider()
                    }
                    is KwikDropdownItemActionState.Data -> {
                        KwikDropdownItemView(
                            data = item.action,
                            onClick = {
                                onDismissRequest()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun KwikDropdownItemView(
    data: KwikDropdownItem,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                onClick()
                data.onClick()
            },
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        data.leadingIcon?.invoke()
        KwikText.BodyMedium(
            text = data.text()
        )
        data.trailingIcon?.invoke()
        if (data.divider) {
            HorizontalDivider()
        }
    }
}

data class KwikDropdownItem(
    val id: UUID = UUID.randomUUID(),
    val text: @Composable () -> Unit,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null,
    val divider: Boolean = false
)

sealed class KwikDropdownItemActionState {
    data class Header(val title: String) : KwikDropdownItemActionState()
    data class Space(val id: UUID = UUID.randomUUID()) : KwikDropdownItemActionState()
    data class Divider(val id: UUID = UUID.randomUUID()) : KwikDropdownItemActionState()
    data class Data(val action: KwikDropdownItem) : KwikDropdownItemActionState()
}