package com.isakaro.kwik.catalog.appabr

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.text.KwikText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KwikAppBar(
    title: String,
    subtitle: String? = null,
    navigationIcon: Any = Icons.AutoMirrored.Filled.ArrowBack,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    elevation: Dp = 2.dp,
    navigationClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = Color.White,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.shadow(
            elevation,
            ambientColor = MaterialTheme.colorScheme.onSurface,
            spotColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Column {
                KwikText.TitleMedium(
                    text = title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if(subtitle != null){
                    KwikText.BodyMedium(
                        text = subtitle,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.onSurface,
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
                KwikImageView(
                    url = navigationIcon,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
    )
}
