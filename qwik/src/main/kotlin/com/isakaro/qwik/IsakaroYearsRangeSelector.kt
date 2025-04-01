package com.isakaro.qwik

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isakaro.screens.home.search.vehicles.VehicleYearFiltersData
import com.isakaro.screens.home.search.vehicles.VehiclesSearchViewModel
import com.isakaro.qwik.theme.ColorSecondaryAccent
import java.time.Year

@Composable
fun QwikYearsRangeSelector(
    viewModel: VehiclesSearchViewModel = hiltViewModel(),
    onYearRangeChanged: (Int, Int) -> Unit
) {
    val currentYear = Year.now().value
    val oldestYear = 1965
    val yearRange = (oldestYear..currentYear).sortedByDescending { it }
    val startYearVisible = remember { mutableStateOf(false) }
    val endYearVisible = remember { mutableStateOf(false) }
    val vehicleYearsData by viewModel.vehicleYearsData.collectAsState()

    var selectedMinYear by remember {
        mutableIntStateOf(vehicleYearsData.minYear)
    }
    var selectedMaxYear by remember {
        mutableIntStateOf(vehicleYearsData.maxYear)
    }

    LaunchedEffect(selectedMinYear, selectedMaxYear) {
        viewModel.updateSearchFilters {
            minYear = selectedMinYear
            maxYear = selectedMaxYear
        }
        viewModel.localData.saveVehicleYearSearchData(
            VehicleYearFiltersData(
                minYear = selectedMinYear,
                maxYear = selectedMaxYear
            )
        )
        onYearRangeChanged(selectedMinYear, selectedMaxYear)
        viewModel.updateYears()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "Year Range",
            style = MaterialTheme.typography.titleMedium
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
                Text(
                    text = "From Year",
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.padding(end = 2.dp),
                        text = selectedMinYear.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        tint = ColorSecondaryAccent,
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
                Text(
                    text = "To Year",
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                   Text(
                       modifier = Modifier.padding(end = 2.dp),
                       text = selectedMaxYear.toString(),
                       style = MaterialTheme.typography.titleSmall
                   )
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        tint = ColorSecondaryAccent,
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
                            Text(year.toString())
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