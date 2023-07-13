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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.color.ColorDialog
import com.maxkeppeler.sheets.color.models.ColorConfig
import com.maxkeppeler.sheets.color.models.ColorSelection
import com.maxkeppeler.sheets.color.models.ColorSelectionMode
import com.maxkeppeler.sheets.color.models.MultipleColors
import com.maxkeppeler.sheets.color.models.SingleColor
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import com.maxkeppeler.sheets.duration.DurationDialog
import com.maxkeppeler.sheets.duration.models.DurationConfig
import com.maxkeppeler.sheets.duration.models.DurationFormat
import com.maxkeppeler.sheets.duration.models.DurationSelection
import com.vsnappy1.datepicker.DatePicker
import com.vsnappy1.datepicker.data.DefaultDatePickerConfig
import com.vsnappy1.datepicker.data.model.DefaultDate
import com.vsnappy1.datepicker.ui.model.DatePickerConfiguration
import okhttp3.internal.toHexString
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

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
                        DateTimePickerDialogBox()
                        ColorPickerDialog()
                        ColorPickerDialog2()
                        DurationDialogBox()
                        DatePickerDialogScreen2()
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
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { pickerOpen = !pickerOpen }),
            selection = ClockSelection.HoursMinutesSeconds { hours, minutes, seconds ->
                selectedTime.value = LocalTime.of(hours, minutes, seconds)
            },
            config = ClockConfig(is24HourFormat = true, defaultTime = LocalTime.now()),
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
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
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { calendarState = !calendarState }),
            selection = CalendarSelection.Date(
                selectedDate = selectedDate.value
            ) { newDate -> selectedDate.value = newDate },
            config = CalendarConfig(yearSelection = true, style = CalendarStyle.MONTH)
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { calendarState = true }) { Text("Open Date Picker") }
        Text(selectedDate.value.toString())
    }
}

@Composable
fun DateTimePickerDialogBox() {
    var dialogState by remember { mutableStateOf(false) }
    val selectedDateTime =
        remember { mutableStateOf<LocalDateTime?>(LocalDateTime.now().plusDays(1)) }

    if (dialogState) {
        DateTimeDialog(
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { dialogState = !dialogState }
            ),
            selection = DateTimeSelection.DateTime(
                selectedTime = selectedDateTime.value?.toLocalTime()
            ) { newDateTime -> selectedDateTime.value = newDateTime }
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { dialogState = true }) { Text("Open Date-Time Picker") }
        Text(selectedDateTime.value.toString())
    }
}

val templateColors = MultipleColors.ColorsInt(
    Color.Red.copy(0.1f).toArgb(),
    Color.Red.copy(0.3f).toArgb(),
    Color.Red.copy(0.5f).toArgb(),
    Color.Red.toArgb(),
    Color.Green.toArgb(),
    Color.Yellow.toArgb(),
)

@Composable
fun ColorPickerDialog() {
    var dialogState by remember { mutableStateOf(false) }
    val color = remember { mutableStateOf<Int?>(Color.Blue.toArgb()) }

    if (dialogState) {
        ColorDialog(
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { dialogState = !dialogState }),
            selection = ColorSelection(
                onSelectColor = { color.value = it },
                onSelectNone = { color.value = null },
            ),
            config = ColorConfig(templateColors = templateColors)
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { dialogState = true }) { Text("Open Color Picker") }
        Text(color.value!!.toHexString())
    }
}

@Composable
fun ColorPickerDialog2() {
    var dialogState by remember { mutableStateOf(false) }
    val color = remember { mutableStateOf<Int?>(Color.Blue.toArgb()) }

    if (dialogState) {
        ColorDialog(
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { dialogState = !dialogState }),
            selection = ColorSelection(
                selectedColor = SingleColor(color.value),
                onSelectColor = { color.value = it },
            ),
            config = ColorConfig(
                templateColors = templateColors,
                defaultDisplayMode = ColorSelectionMode.CUSTOM,
                allowCustomColorAlphaValues = false
            )
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { dialogState = true }) { Text("Open Color Picker") }
        Text(color.value!!.toHexString())
    }
}

@Composable
fun DurationDialogBox() {
    var dialogState by remember { mutableStateOf(false) }
    val timeInSeconds = remember { mutableLongStateOf(240) }

    if (dialogState) {
        DurationDialog(
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { dialogState = !dialogState }),
            selection = DurationSelection { newTime -> timeInSeconds.longValue = newTime },
            config = DurationConfig(
                timeFormat = DurationFormat.HH_MM_SS,
                currentTime = timeInSeconds.longValue
            )
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { dialogState = true }) { Text("Open Duration Picker") }
        Text(timeInSeconds.longValue.toDuration(DurationUnit.SECONDS).toString())
    }
}

@Composable
fun DatePickerDialogScreen2() {
    var calendarState by remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf(LocalDate.now().minusDays(3)) }

    if (calendarState) {
        Dialog(
            onDismissRequest = { calendarState = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            DatePicker(
                modifier = Modifier
                    .padding(10.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                onDateSelected = { y, m, d -> selectedDate.value = LocalDate.of(y, m, d) },
                date = DefaultDate.defaultDate,
                configuration = DatePickerConfiguration.Builder()
                    .dateTextStyle(
                        DefaultDatePickerConfig.dateTextStyle.copy(
                            color = Color(
                                0xFF333333
                            )
                        )
                    )
                    .selectedDateTextStyle(textStyle = TextStyle(Color(0xFFFFFFFF)))
                    .selectedDateBackgroundColor(Color(0xFF64DD17))
                    .build()
            )
        }
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { calendarState = true }) { Text("Open Date Picker") }
        Text(selectedDate.value.toString())
    }
}
