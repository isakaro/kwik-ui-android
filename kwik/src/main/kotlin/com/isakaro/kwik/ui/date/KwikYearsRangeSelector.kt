package com.isakaro.kwik.ui.date

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.isakaro.kwik.ui.text.KwikText

import java.util.Calendar

/**
 * @param minYear The minimum year that can be selected.
 * @param maxYear The maximum year that can be selected.
 * */
data class KwikYearsData(
    val minYear: Int = 1965,
    val maxYear: Int = Calendar.getInstance().get(Calendar.YEAR)
)

/**
 * A composable that displays a year range selector.
 *
 * @param title The title of the year range selector.
 * @param fromYearText The text to display for the "From Year" label.
 * @param toYearText The text to display for the "To Year" label.
 * @param kwikYearsData [KwikYearsData] The data to use for the year range selector.
 * @param onYearRangeChanged A callback that is called when the year range is changed.
 * */
@Composable
fun KwikYearsRangeSelector(
    title: String = "Year Range",
    fromYearText: String = "From Year",
    toYearText: String = "To Year",
    kwikYearsData: KwikYearsData = KwikYearsData(),
    onYearRangeChanged: (Int, Int) -> Unit
) {
    val currentYear = kwikYearsData.maxYear
    val oldestYear = kwikYearsData.minYear
    val yearRange = (oldestYear..currentYear).sortedByDescending { it }
    val startYearVisible = remember { mutableStateOf(false) }
    val endYearVisible = remember { mutableStateOf(false) }

    var selectedMinYear by remember {
        mutableIntStateOf(oldestYear)
    }
    var selectedMaxYear by remember {
        mutableIntStateOf(currentYear)
    }

    LaunchedEffect(selectedMinYear, selectedMaxYear) {
        onYearRangeChanged(selectedMinYear, selectedMaxYear)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        KwikText.TitleMedium(
            text = title
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                    startYearVisible.value = true
                }
            ) {
                KwikText.BodySmall(
                    text = fromYearText
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    KwikText.TitleSmall(
                        modifier = Modifier.padding(end = 2.dp),
                        text = selectedMinYear.toString()
                    )
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(25.dp),
                        contentDescription = null
                    )
                }
            }

            DropdownMenu(
                expanded = startYearVisible.value,
                onDismissRequest = { startYearVisible.value = false },
                containerColor = Color.White,
            ) {
                yearRange.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year.toString()) },
                        onClick = {
                            selectedMinYear = year
                            if (year > selectedMaxYear) {
                                selectedMaxYear = year
                            }
                            startYearVisible.value = false
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                    endYearVisible.value = true
                },
                horizontalAlignment = Alignment.End
            ) {
                KwikText.BodySmall(
                    text = toYearText
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                   KwikText.TitleSmall(
                       modifier = Modifier.padding(end = 2.dp),
                       text = selectedMaxYear.toString()
                   )
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(25.dp),
                        contentDescription = null
                    )
               }
            }

            DropdownMenu(
                modifier = Modifier.align(Alignment.CenterEnd),
                expanded = endYearVisible.value,
                onDismissRequest = { endYearVisible.value = false },
                containerColor = Color.White,
            ) {
                yearRange.forEach { year ->
                    DropdownMenuItem(
                        text = {
                            KwikText.BodyMedium(text = year.toString())
                        },
                        onClick = {
                            selectedMaxYear = year
                            if (year < selectedMinYear) {
                                selectedMinYear = year
                            }
                            endYearVisible.value = false
                        }
                    )
                }
            }
        }
    }
}