package com.isakaro.ui.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.isakaro.ui.theme.ColorPrimaryAccent

@Composable
fun IsakaroStepper(
    modifier: Modifier = Modifier,
    steps: List<String>,
    currentStepIndex: Int,
    activeStepColor: Color = ColorPrimaryAccent,
    completed: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            StepperItem(
                modifier = Modifier.weight(1F),
                stepsCount = steps.size,
                stepIndex = index,
                isComplete = index < currentStepIndex || completed,
                isCurrent = index == currentStepIndex,
                label = step,
                activeStepColor = activeStepColor,
                currentStepIndex = currentStepIndex,
                isLastStep = index == steps.lastIndex
            )
        }
    }
}

@Composable
private fun StepperItem(
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
            Color.Black
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
                    Text(
                        text = (stepIndex + 1).toString(),
                        color = Color.White,
                        style = if(stepsCount > 5) MaterialTheme.typography.bodySmall else MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }

        Text(
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

object Steps {
    const val STEP_1 = 0
    const val STEP_2 = 1
    const val STEP_3 = 2
    const val STEP_4 = 3
    const val STEP_5 = 4
    const val STEP_6 = 5
    const val STEP_7 = 6
    const val STEP_8 = 7
}

@Preview
@Composable
fun IsakaroStepperPreview() {
    IsakaroStepper(
        steps = listOf("Step 1", "Step 2", "Step 3", "Step 4"),
        currentStepIndex = 2
    )
}