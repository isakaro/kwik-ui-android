package app.isakaro.appcatalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import com.isakaro.ui.catalog.navigator.NavigationRoute
import com.isakaro.ui.catalog.navigator.NavigationRoute.AppBarScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.BottomNavScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.ButtonScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.CardScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.CheckBoxScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.ColorScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.DialogScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.DropDownScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.ProgressIndicatorScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.RadioButtonScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.ShapeScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.SliderScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.SnackBarScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.SwitchScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.TextFieldScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.TypographyScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.TabScreen
import com.isakaro.ui.catalog.navigator.NavigationRoute.BottomSheetScreen
import app.isakaro.ui.library.theme.Theme.AmpersandTheme

@Composable
internal fun StartScreen(
    navClick: (NavigationRoute) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColumnShowcase(title = "Colors") { navClick(ColorScreen) }
        ColumnShowcase(title = "Typography") { navClick(TypographyScreen) }
        ColumnShowcase(title = "Shape") { navClick(ShapeScreen) }
        ColumnShowcase(title = "AppBar") { navClick(AppBarScreen) }
        ColumnShowcase(title = "Permissions") { navClick(NavigationRoute.PermissionScreen) }
        ColumnShowcase(title = "Buttons") { navClick(ButtonScreen) }
        ColumnShowcase(title = "Tabs") { navClick(TabScreen) }
        ColumnShowcase(title = "BottomSheet") { navClick(BottomSheetScreen) }
        ColumnShowcase(title = "Bottom Navigation") { navClick(BottomNavScreen) }
        ColumnShowcase(title = "Card") { navClick(CardScreen) }
        ColumnShowcase(title = "CheckBox") { navClick(CheckBoxScreen) }
        ColumnShowcase(title = "Dialog") { navClick(DialogScreen) }
        ColumnShowcase(title = "DropDown") { navClick(DropDownScreen) }
        ColumnShowcase(title = "Progress Indicator") { navClick(ProgressIndicatorScreen) }
        ColumnShowcase(title = "Radio Button") { navClick(RadioButtonScreen) }
        ColumnShowcase(title = "Slider") { navClick(SliderScreen) }
        ColumnShowcase(title = "SnackBar") { navClick(SnackBarScreen) }
        ColumnShowcase(title = "Switch") { navClick(SwitchScreen) }
        ColumnShowcase(title = "TextField") { navClick(TextFieldScreen) }
        ColumnShowcase(title = "Accordion") { navClick(NavigationRoute.AccordionScreen) }
    }
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
    AmpersandTheme {
        StartScreen()
    }
}
