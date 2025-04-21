package com.isakaro.kwik.ui.stepper

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.isakaro.kwik.ui.text.KwikText

/**
 * The state of the stepper
 *
 * @param steps: The list of steps to display in the stepper.
 * @param currentStep: The current step of the stepper. Starts from 0.
 * @param isComplete: Whether the stepper is complete or not. Default is false.
 * */
data class KwikStepperState(
    val steps: List<String>,
    var currentStep: Int = 0,
    var isComplete: Boolean = false
)

/**
 * A state holder for the stepper. Refer to [KwikStepperState]
 *
 * @param steps: The list of steps to display in the stepper.
 * */
@Composable
fun rememberKwikStepperState(steps: List<String>): MutableState<KwikStepperState> {
    return remember { mutableStateOf(KwikStepperState(steps)) }
}

// this will complete the stepper, marking all steps as complete
fun MutableState<KwikStepperState>.completeAll() {
    this.value = KwikStepperState(
        steps = this.value.steps,
        currentStep = this.value.steps.size,
        isComplete = true
    )
}

// clear all steps and reset the stepper
fun MutableState<KwikStepperState>.clearAll() {
    this.value = KwikStepperState(
        steps = this.value.steps,
        currentStep = 0,
        isComplete = false
    )
}

/**
 * Allows to set new steps to the stepper
 *
 * @param steps: The list of steps to display in the stepper.
 * @param index: The index of the step to move to. Starts from 0.
 * */
fun MutableState<KwikStepperState>.setNewSteps(
    steps: List<String>,
    index: Int = 0
) {
    this.value = KwikStepperState(
        steps = steps,
        currentStep = index,
        isComplete = false
    )
}

/**
* Move to a specific step
*
* @param index: The index of the step to move to. Starts from 0.
* */
fun MutableState<KwikStepperState>.moveToStep(index: Int) {
    if(index in 0..this.value.steps.lastIndex) {
        this.value = KwikStepperState(
            steps = this.value.steps,
            currentStep = index,
            isComplete = this.value.currentStep >= this.value.steps.size
        )
    }
}

// move to the next step
fun MutableState<KwikStepperState>.moveForward() {
    if(this.value.currentStep < this.value.steps.size) {
        this.value = KwikStepperState(
            steps = this.value.steps,
            currentStep = this.value.currentStep + 1,
            isComplete = this.value.currentStep >= this.value.steps.size
        )
    }
}

// move to the previous step
fun MutableState<KwikStepperState>.moveBackward() {
    if(this.value.currentStep > 0) {
        this.value = KwikStepperState(
            steps = this.value.steps,
            currentStep = this.value.currentStep - 1,
            isComplete = this.value.currentStep >= this.value.steps.size
        )
    }
}

/**
 * A stepper component that displays a series of steps with indicators.
 *
 * @param state: The state of the stepper. Refer to [KwikStepperState]
 * @param modifier Modifier to be applied to the stepper.
 * @param activeStepColor Color of the active step indicator.
 * */
@Composable
fun KwikStepper(
    state: MutableState<KwikStepperState>,
    modifier: Modifier = Modifier,
    activeStepColor: Color = MaterialTheme.colorScheme.primary
) {

    var completed by remember { mutableStateOf(false) }

    LaunchedEffect(state.value) {
        if (state.value.currentStep > state.value.steps.size) {
            completed = true
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        state.value.steps.forEachIndexed { index, step ->
            KwikStepperItem(
                modifier = Modifier.weight(1F),
                stepsCount = state.value.steps.size,
                stepIndex = index,
                isComplete = index < state.value.currentStep || completed,
                isCurrent = index == state.value.currentStep,
                label = step,
                activeStepColor = activeStepColor,
                currentStepIndex = state.value.currentStep,
                isLastStep = index == state.value.steps.lastIndex
            )
        }
    }
}

@Composable
private fun KwikStepperItem(
    modifier: Modifier = Modifier,
    stepsCount: Int,
    stepIndex: Int,
    currentStepIndex: Int,
    isComplete: Boolean,
    isCurrent: Boolean,
    label: String,
    activeStepColor: Color,
    isLastStep: Boolean,
) {
    val transition = updateTransition(isComplete, label = "")

    val activeColor by transition.animateColor(label = "color") { if (it || isCurrent) activeStepColor else Color.Gray }
    val indicatorColor by transition.animateColor(label = "indicatorColor") { if (it || isCurrent) activeStepColor else Color.Gray }
    val labelColor by transition.animateColor(label = "labelColor") {
        if (it || isCurrent) {
            MaterialTheme.colorScheme.primary
        } else if(isCurrent) {
            activeStepColor
        } else {
            Color.Gray
        }
    }

    ConstraintLayout(modifier = modifier) {
        val (indicator, stepLabel, line) = createRefs()

        Surface(
            shape = CircleShape,
            color = indicatorColor,
            modifier = Modifier
                .size(if(stepsCount > 5) 25.dp else 30.dp)
                .constrainAs(indicator) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isComplete) {
                    Icon(
                        imageVector = Icons.Default.Done, "complete",
                        modifier = modifier.padding(4.dp),
                        tint = Color.White
                    )
                } else {
                    KwikText.RenderText(
                        text = (stepIndex + 1).toString(),
                        color = Color.White,
                        style = if (stepsCount > 5) MaterialTheme.typography.bodySmall else MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        KwikText.RenderText(
            modifier = Modifier.constrainAs(stepLabel) {
                start.linkTo(indicator.start)
                top.linkTo(indicator.bottom)
                end.linkTo(indicator.end)
                bottom.linkTo(parent.bottom)
            },
            style = if(stepsCount > 5) MaterialTheme.typography.displaySmall else MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            text = label,
            color = labelColor,
        )

        if (!isLastStep) {
            HorizontalDivider(
                modifier = Modifier
                    .constrainAs(line) {
                        top.linkTo(indicator.top)
                        bottom.linkTo(indicator.bottom)
                        start.linkTo(indicator.end)
                    },
                color = if(currentStepIndex > stepIndex) activeColor else Color.Gray,
                thickness = 2.dp,
            )
        }
    }
}

@Preview
@Composable
private fun KwikStepperPreview() {
    val state = rememberKwikStepperState(
        listOf("Step 1", "Step 2", "Step 3", "Step 4")
    )

    KwikStepper(
        state = state
    )
}