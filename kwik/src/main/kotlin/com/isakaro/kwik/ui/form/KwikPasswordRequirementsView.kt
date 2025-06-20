package com.isakaro.kwik.ui.form

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isakaro.kwik.ui.theme.KwikColorSuccess

/**
 * A view that displays password requirements.
 *
 * @param requirements a map of [KwikPasswordRequirementType] to [PasswordRequirement] objects
 * @param formSubmitAttempted whether the form has been submitted. When true, the view will display errors if requirements are not met.
 *
 * Example usage:
 *
 * ```
 * val PasswordRequirements = mapOf(
 *     KwikPasswordRequirementType.LENGTH to PasswordRequirement("At least 8 characters", false),
 *     KwikPasswordRequirementType.NUMBER to PasswordRequirement("Contains a number", false),
 *     KwikPasswordRequirementType.CHAR to PasswordRequirement("Contains a letter", false)
 * )
 *
 * KwikPasswordRequirementsView(
 *         requirements = PasswordRequirements,
 *         formSubmitAttempted = false
 * )
 * ```
 * */
@Composable
fun KwikPasswordRequirementsView(
    modifier: Modifier = Modifier,
    requirements: Map<KwikPasswordRequirementType, PasswordRequirement>,
    formSubmitAttempted: Boolean,
) {
    val allRequirementsMet = requirements.all { it.value.requirementMet }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(
                2.dp,
                if (allRequirementsMet) KwikColorSuccess else if (formSubmitAttempted) MaterialTheme.colorScheme.error else Color.Gray,
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
            tint = if (requirement.requirementMet) KwikColorSuccess else if (formSubmitAttempted) MaterialTheme.colorScheme.error else Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = requirement.description,
            fontSize = 14.sp,
            color = if (requirement.requirementMet) KwikColorSuccess else if (formSubmitAttempted) MaterialTheme.colorScheme.error else Color.Black,
            fontWeight = FontWeight.Normal
        )
    }
}

enum class KwikPasswordRequirementType {
    LENGTH, NUMBER, CHAR
}

data class PasswordRequirement(
    val description: String,
    var requirementMet: Boolean,
    var visible: Boolean = true
)

val PasswordRequirements = mapOf(
    KwikPasswordRequirementType.LENGTH to PasswordRequirement("At least 8 characters", false),
    KwikPasswordRequirementType.NUMBER to PasswordRequirement("Contains a number", false),
    KwikPasswordRequirementType.CHAR to PasswordRequirement("Contains a letter", false)
)

@Preview
@Composable
private fun KwikPasswordRequirementsViewPreview() {
    KwikPasswordRequirementsView(
        requirements = PasswordRequirements,
        formSubmitAttempted = false
    )
}