package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

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
                    AlertDialogSample()
                }
            }
        }
    }
}

@Composable
fun AlertDialogSample() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
