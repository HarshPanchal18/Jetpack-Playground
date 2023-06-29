package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.input.InputDialog
import com.maxkeppeler.sheets.input.models.InputDivider
import com.maxkeppeler.sheets.input.models.InputHeader
import com.maxkeppeler.sheets.input.models.InputRadioButtonGroup
import com.maxkeppeler.sheets.input.models.InputSelection
import com.maxkeppeler.sheets.input.models.InputText

class AlertDialogActivity : ComponentActivity() {
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
                        AlertDialogSample()
                        InputDialogBox()
                    }
                }
            }
        }
    }
}

@Composable
fun AlertDialogSample() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val openDialog = remember { mutableStateOf(false) }

        Button(onClick = { openDialog.value = !openDialog.value })
        { Text("Click here") }

        if (openDialog.value) {
            AlertDialog(
                title = { Text("Title is here") },
                text = { Text("Here is description") },
                confirmButton = {
                    Button(onClick = { openDialog.value = !openDialog.value })
                    { Text("Confirm Button") }
                },
                onDismissRequest = { openDialog.value = !openDialog.value },
                dismissButton = {
                    Button(onClick = { openDialog.value = !openDialog.value })
                    { Text("Dismiss Button") }
                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialogBox() {
    var dialogState by remember { mutableStateOf(false) }
    val items = remember { mutableListOf("Github", "Twitter", "LinkedIn", "Other") }
    var selectionIndex by remember { mutableIntStateOf(0) }
    val inputOptions = listOf(
        InputText("Thanks for taking part on this short survey! :)"),
        InputDivider(),
        InputRadioButtonGroup(
            header = InputHeader(
                title = "Where did you read about sheets?",
                body = "It helps us determine where to be more active.",
                icon = IconSource(Icons.Filled.Language)
            ),
            items = items,
            required = true,
            key = "Source"
        )
    )

    if (dialogState) {
        InputDialog(
            state = rememberUseCaseState(
                visible = true,
                onCloseRequest = { dialogState = !dialogState }),
            selection = InputSelection(
                input = inputOptions,
                onPositiveClick = { result -> selectionIndex = result.getInt("Source") }
            ),
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { dialogState = true }) { Text("Open Survey Dialog") }
        Text(items[selectionIndex])
    }
}
