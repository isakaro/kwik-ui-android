package com.isakaro.kwik.catalog.stepper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.button.KwikButton
import com.isakaro.kwik.ui.button.KwikIconButton
import com.isakaro.kwik.ui.stepper.KwikStepper
import com.isakaro.kwik.animations.SlideInFromRightAnimations
import com.isakaro.kwik.catalog.ShowCase
import com.isakaro.kwik.catalog.ShowCaseContainer
import com.isakaro.kwik.ui.stepper.clearAll
import com.isakaro.kwik.ui.stepper.completeAll
import com.isakaro.kwik.ui.stepper.moveBackward
import com.isakaro.kwik.ui.stepper.moveForward
import com.isakaro.kwik.navigator
import com.isakaro.kwik.ui.stepper.rememberKwikStepperState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(style = SlideInFromRightAnimations::class)
internal fun KwikStepperScreen(
    navigator: DestinationsNavigator = navigator()
) {

    val kwikStepperState = rememberKwikStepperState(
        steps = listOf("Request", "Verify", "Dispatch", "Delivered")
    )

    ShowCaseContainer(
        title = "Stepper",
        onBackClick = {
            navigator.navigateUp()
        }
    ) {
        ShowCase(title = "Stepper") {
            KwikStepper(
                state = kwikStepperState
            )

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                KwikIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    onClick = {
                        kwikStepperState.moveBackward()
                    }
                )

                KwikIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    onClick = {
                        kwikStepperState.moveForward()
                    }
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp, alignment = Alignment.CenterVertically)
            ) {
                KwikButton(
                    text = "Complete all",
                    onClick = {
                        kwikStepperState.completeAll()
                    }
                )

                KwikButton(
                    text = "Clear all",
                    onClick = {
                        kwikStepperState.clearAll()
                    }
                )
            }
        }
    }
}

