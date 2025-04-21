package com.isakaro.kwik.ui.dropdown

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
import com.isakaro.kwik.ui.spacer.KwikVSpacer
import com.isakaro.kwik.ui.text.KwikText
import java.util.UUID

/**
 * A dropdown menu component that allows the user to select an item from a list of options.
 *
 * @param state: The state of the dropdown menu. If true, the dropdown menu will be shown.
 * @param shape: The shape of the dropdown menu. Default is MaterialTheme.shapes.medium.
 * @param onDismissRequest: The callback that will be called when the dropdown menu is dismissed.
 * @param items: The list of items to be displayed in the dropdown menu. Each item can be a header, space, divider, or data.
 * */
@Composable
fun KwikDropdown(
    state: Boolean,
    shape: Shape = MaterialTheme.shapes.medium,
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

/**
 * A composable function that represents an item in the dropdown menu.
 *
 * @param data: The data for the item.
 * @param onClick: The callback that will be called when the item is clicked.
 * */
@Composable
private fun KwikDropdownItemView(
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

/**
 * A data class representing an item in the dropdown menu.
 *
 * @param id: The unique identifier for the item.
 * @param text: The text to be displayed for the item.
 * @param onClick: The callback that will be called when the item is clicked.
 * @param enabled: A boolean indicating whether the item is enabled or not. Default is true.
 * @param leadingIcon: A composable function that represents the leading icon for the item. Default is null.
 * @param trailingIcon: A composable function that represents the trailing icon for the item. Default is null.
 * @param divider: A boolean indicating whether to show a divider after the item. Default is false.
 * */
data class KwikDropdownItem(
    val id: UUID = UUID.randomUUID(),
    val text: @Composable () -> Unit,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null,
    val divider: Boolean = false
)

/**
 * A sealed class representing the different states of the dropdown menu items.
 *
 * [Header]: Represents a header item in the dropdown menu.
 * [Space]: Represents a space item in the dropdown menu.
 * [Divider] : Represents a divider item in the dropdown menu.
 * [Data]: Represents a data item in the dropdown menu.
 * */
sealed class KwikDropdownItemActionState {
    data class Header(val title: String) : KwikDropdownItemActionState()
    data class Space(val id: UUID = UUID.randomUUID()) : KwikDropdownItemActionState()
    data class Divider(val id: UUID = UUID.randomUUID()) : KwikDropdownItemActionState()
    data class Data(val action: KwikDropdownItem) : KwikDropdownItemActionState()
}