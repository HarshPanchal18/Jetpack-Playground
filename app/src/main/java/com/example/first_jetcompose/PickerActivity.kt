@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.first_jetcompose

import android.os.Bundle
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime

class PickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TimePickerDialogBox()
                        DatePickerDialogScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun TimePickerDialogBox() {
    var pickerOpen by remember { mutableStateOf(false) }
    val selectedTime = remember { mutableStateOf(LocalTime.now().withNano(0)) }

    if (pickerOpen) {
        ClockDialog(
            state = rememberUseCaseState(visible = true, onCloseRequest = {}),
            selection = ClockSelection.HoursMinutesSeconds { hours, minutes, seconds ->
                selectedTime.value = LocalTime.of(hours, minutes, seconds)
                pickerOpen = !pickerOpen
            },
            config = ClockConfig(is24HourFormat = true, defaultTime = LocalTime.now()),
        )
    }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { pickerOpen = true }) { Text("Open Time Picker") }
        Text(selectedTime.value.toString())
    }
}

@Composable
fun DatePickerDialogScreen() {
    var calendarState by remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf(LocalDate.now().minusDays(3)) }

    if (calendarState) {
        CalendarDialog(
            state = rememberUseCaseState(visible = true, embedded = true, onCloseRequest = {}),
            selection = CalendarSelection.Date(selectedDate = selectedDate.value) { newDate ->
                selectedDate.value = newDate
                calendarState = !calendarState
            },
            config = CalendarConfig(yearSelection = true, style = CalendarStyle.MONTH)
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { calendarState = true }) {Text("Open Date Picker") }
        Text(selectedDate.value.toString())
    }
}
