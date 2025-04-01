package app.isakaro.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QwikAppBar(
    title: String,
    subtitle: String? = null,
    navigationIcon: Any = Icons.AutoMirrored.Filled.ArrowBack,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.background,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    elevation: Dp = 0.dp,
    navigationClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarColors(
            actionIconContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            scrolledContainerColor = Color.White,
            containerColor = backgroundColor,
            titleContentColor = Color.Black
        ),
        title = {
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )
                if(subtitle != null){
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        maxLines = 2,
                        color = Color.Gray,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = navigationClick
            ) {
                QwikImageView(
                    url = navigationIcon
                )
            }
        },
    )
}