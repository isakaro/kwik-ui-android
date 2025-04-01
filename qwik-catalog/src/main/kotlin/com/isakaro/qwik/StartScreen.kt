package com.isakaro.appcatalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isakaro.qwik.QwikLazyList
import com.isakaro.qwik.QwikListItemAction
import com.isakaro.qwik.QwikListItemActionState
import com.isakaro.qwik.catalog.navigator.NavigationRoute
import com.isakaro.qwik.catalog.navigator.NavigationRoute.AppBarScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.BottomNavScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.ButtonScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.CardScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.CheckBoxScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.ColorScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.DialogScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.DropDownScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.ProgressIndicatorScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.RadioButtonScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.ShapeScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.SliderScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.SnackBarScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.SwitchScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.TextFieldScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.TypographyScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.TabScreen
import com.isakaro.qwik.catalog.navigator.NavigationRoute.BottomSheetScreen

@Composable
internal fun StartScreen(
    navClick: (NavigationRoute) -> Unit = {}
) {

    val components = listOf(
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Colors",
                action = { navClick(ColorScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Typography",
                action = { navClick(TypographyScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Shape",
                action = { navClick(ShapeScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "AppBar",
                action = { navClick(AppBarScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Permissions",
                action = { navClick(NavigationRoute.PermissionScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Buttons",
                action = { navClick(ButtonScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Tabs",
                action = { navClick(TabScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "BottomSheet",
                action = { navClick(BottomSheetScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Bottom Navigation",
                action = { navClick(BottomNavScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Card",
                action = { navClick(CardScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "CheckBox",
                action = { navClick(CheckBoxScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Dialog",
                action = { navClick(DialogScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "DropDown",
                action = { navClick(DropDownScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Progress Indicator",
                action = { navClick(ProgressIndicatorScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Radio Button",
                action = { navClick(RadioButtonScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Slider",
                action = { navClick(SliderScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "SnackBar",
                action = { navClick(SnackBarScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Switch",
                action = { navClick(SwitchScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "TextField",
                action = { navClick(TextFieldScreen) }
            )
        ),
        QwikListItemActionState.Data(
            QwikListItemAction(
                title = "Accordion",
                action = { navClick(NavigationRoute.AccordionScreen) }
            )
        )
    ).sortedBy { it.action.title }

    val listState = rememberLazyListState()

    QwikLazyList(
        state = listState,
        components = components
    )
}

@Composable
fun ColumnShowcase(title: String, click: () -> Unit) {
    Card(
        onClick = click,
        modifier = Modifier
            .padding(8.dp)
            .heightIn(min = 48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ){
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    StartScreen()
}
