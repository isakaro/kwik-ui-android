package com.isakaro.kwik.catalog.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.dropdown.KwikDropdown
import com.isakaro.kwik.ui.dropdown.KwikDropdownItem
import com.isakaro.kwik.ui.dropdown.KwikDropdownItemActionState
import com.isakaro.kwik.ui.button.KwikIconButton
import com.isakaro.kwik.ui.image.KwikImageView
import com.isakaro.kwik.ui.text.KwikText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikDropDownScreen(
    navigator: DestinationsNavigator = navigator()
) {
    ShowCaseContainer(
        title = "Dropdown",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "DropDown") {
            var state by remember { mutableStateOf(false) }
            val items = listOf(
                KwikDropdownItemActionState.Header("Account"),
                KwikDropdownItemActionState.Data(
                    KwikDropdownItem(
                        text = {
                            KwikText.BodyMedium(text = "Profile")
                        },
                        leadingIcon = {
                            KwikImageView(
                                url = Icons.Default.AccountCircle
                            )
                        },
                        onClick = {

                        }
                    )
                ),
                KwikDropdownItemActionState.Data(
                    KwikDropdownItem(
                        text = {
                            KwikText.BodyMedium(text = "Settings")
                        },
                        leadingIcon = {
                            KwikImageView(
                                url = Icons.Default.Settings
                            )
                        },
                        onClick = {

                        }
                    )
                ),
                KwikDropdownItemActionState.Data(
                    KwikDropdownItem(
                        text = {
                            KwikText.BodyMedium(text = "Logout")
                        },
                        leadingIcon = {
                            KwikImageView(
                                url = Icons.AutoMirrored.Filled.ExitToApp
                            )
                        },
                        onClick = {

                        }
                    )
                ),
                KwikDropdownItemActionState.Header("System"),
                KwikDropdownItemActionState.Data(
                    KwikDropdownItem(
                        text = {
                            KwikText.BodyMedium(text = "Dark mode")
                        },
                        leadingIcon = {
                            KwikImageView(
                                url = Icons.Default.Settings
                            )
                        },
                        onClick = {

                        }
                    )
                ),
                KwikDropdownItemActionState.Data(
                    KwikDropdownItem(
                        text = {
                            KwikText.BodyMedium(text = "Feedback")
                        },
                        leadingIcon = {
                            KwikImageView(
                                url = Icons.AutoMirrored.Filled.Send
                            )
                        },
                        onClick = {

                        }
                    )
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
            ) {
                KwikIconButton(
                    icon = Icons.Default.MoreVert,
                    onClick = {
                        state = true
                    }
                )
                KwikDropdown(
                    state = state,
                    onDismissRequest = { state = false },
                    items = items
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDropDownScreen() {
    KwikDropDownScreen()
}
