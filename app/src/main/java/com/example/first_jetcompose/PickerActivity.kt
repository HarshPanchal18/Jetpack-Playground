@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.first_jetcompose

import android.net.Uri
import android.os.Bundle
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
import com.vsnappy1.timepicker.TimePicker
import com.vsnappy1.timepicker.data.DefaultTimePickerConfig
import com.vsnappy1.timepicker.data.model.TimePickerTime
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.ui.model.TimePickerConfiguration
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
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
                        TimePickerDialogScreen2()
                        ImagePicker()
                        MultipleImagePicker()
                        DocumentPicker()
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

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { pickerOpen = true },
            modifier = Modifier.weight(1F)
        ) { Text("Open Time Picker") }
        Text(
            text = selectedTime.value.toString(),
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
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

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { calendarState = true },
            modifier = Modifier.weight(1F)
        ) { Text("Open Date Picker") }
        Text(
            text = selectedDate.value.toString(),
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
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

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { dialogState = true },
            modifier = Modifier.weight(1F)
        ) { Text("Open Date-Time Picker") }
        Text(
            text = selectedDateTime.value.toString(),
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
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

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { dialogState = true },
            modifier = Modifier.weight(1F)
        ) { Text("Open Color Picker") }
        Text(
            text = color.value!!.toHexString(), modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
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

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { dialogState = true },
            modifier = Modifier.weight(1F)
        ) { Text("Open Color Picker") }
        Text(
            text = color.value!!.toHexString(),
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
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

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { dialogState = true },
            modifier = Modifier.weight(1F)
        ) { Text("Open Duration Picker") }
        Text(
            text = timeInSeconds.longValue.toDuration(DurationUnit.SECONDS).toString(),
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
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
                        DefaultDatePickerConfig.dateTextStyle.copy(color = Color(0xFF333333))
                    )
                    .selectedDateTextStyle(textStyle = TextStyle(Color(0xFFFFFFFF)))
                    .selectedDateBackgroundColor(Color(0xFF64DD17))
                    .build()
            )
        }
    }

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { calendarState = true },
            modifier = Modifier.weight(1F)
        ) { Text("Open Date Picker") }
        Text(
            text = selectedDate.value.toString(), modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TimePickerDialogScreen2() {
    var calendarState by remember { mutableStateOf(false) }
    val selectedTime = remember { mutableStateOf(LocalTime.now().withNano(0)) }

    if (calendarState) {
        Dialog(
            onDismissRequest = { calendarState = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            TimePicker(
                modifier = Modifier
                    .padding(10.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                onTimeSelected = { h, m -> selectedTime.value = LocalTime.of(h, m) },
                //is24Hour = true,
                minuteGap = MinuteGap.ONE,
                time = TimePickerTime.getTime(),
                configuration = TimePickerConfiguration.Builder()
                    .timeTextStyle(
                        DefaultTimePickerConfig.timeTextStyle.copy(color = Color(0xFF333333))
                    )
                    .selectedTimeTextStyle(textStyle = TextStyle(Color(0xFFFF0074)))
                    .numberOfTimeRowsDisplayed(count = 5)
                    .selectedTimeScaleFactor(scaleFactor = 1.4F)
                    .build()
            )
        }
    }

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { calendarState = true },
            modifier = Modifier.weight(1F)
        ) { Text("Open Time Picker") }
        Text(
            text = selectedTime.value.toString(), modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ImagePicker() {
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { result.value = it }
    )

    Row {
        Button(onClick = {
            launcher.launch(
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly) // .VideoOnly for Picking Video file
            )
        }, modifier = Modifier.weight(1F)) { Text(text = "Select Image") }

        result.value?.let { image ->
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = image).build()
            )
            Image(
                painter = painter, null,
                modifier = Modifier
                    .size(150.dp, 150.dp)
                    .padding(8.dp)
                    .weight(1F)
            )
        }
    }
}

@Composable
fun MultipleImagePicker() {
    val result = remember { mutableStateOf<List<Uri?>?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { result.value = it }
    )

    Row {
        Button(onClick = {
            launcher.launch(
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }, modifier = Modifier.weight(1F)) { Text(text = "Select Multiple Image") }

        result.value?.let { images ->
            LazyRow(
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.weight(1F)
            ) {
                items(images) { item ->
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item).build()
                    )
                    Image(
                        painter = painter, null,
                        modifier = Modifier.size(150.dp, 150.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DocumentPicker() {
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { result.value = it }
    )

    Row {
        Button(onClick = {
            launcher.launch(arrayOf("application/pdf", "image/png", "image/jpeg"))
        }, modifier = Modifier.weight(1F)) { Text(text = "Select Document") }
        result.value?.let { document ->
            Text(text = "Document Path: ${document.path}", modifier = Modifier.weight(1F))
        }
    }
}
