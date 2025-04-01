package com.isakaro.qwik

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isakaro.qwik.theme.ErrorColor
import com.isakaro.qwik.theme.SuccessColor

@Composable
fun QwikPasswordRequirementsView(
    modifier: Modifier = Modifier,
    requirements: Map<QwikPasswordRequirementType, PasswordRequirement>,
    formSubmitAttempted: Boolean,
) {
    val allRequirementsMet = requirements.all { it.value.requirementMet }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(
                2.dp,
                if (allRequirementsMet) SuccessColor else if (formSubmitAttempted) ErrorColor else Color.Gray,
                RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            requirements.values.filter { it.visible }.forEach { requirement ->
                RequirementRow(
                    requirement = requirement,
                    formSubmitAttempted = formSubmitAttempted
                )
            }
        }
    }
}

@Composable
fun RequirementRow(
    requirement: PasswordRequirement,
    formSubmitAttempted: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "field error",
            tint = if (requirement.requirementMet) SuccessColor else if (formSubmitAttempted) ErrorColor else Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = requirement.description,
            fontSize = 14.sp,
            color = if (requirement.requirementMet) SuccessColor else if (formSubmitAttempted) ErrorColor else Color.Black,
            fontWeight = FontWeight.Normal
        )
    }
}

enum class QwikPasswordRequirementType {
    LENGTH, NUMBER, CHAR
}

data class PasswordRequirement(
    val description: String,
    var requirementMet: Boolean,
    var visible: Boolean = true
)

val PasswordRequirements = mapOf(
    QwikPasswordRequirementType.LENGTH to PasswordRequirement("At least 8 characters", false),
    QwikPasswordRequirementType.NUMBER to PasswordRequirement("Contains a number", false),
    QwikPasswordRequirementType.CHAR to PasswordRequirement("Contains a letter", false)
)

@Preview
@Composable
fun QwikPasswordRequirementsViewPreview() {
    QwikPasswordRequirementsView(
        requirements = PasswordRequirements,
        formSubmitAttempted = false
    )
}