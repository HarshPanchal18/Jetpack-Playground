package com.example.first_jetcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class FullScreenDialogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FullScreenDialog()
                }
            }
        }
    }
}

@Composable
fun FullScreenDialog(
    primaryColor: Color = Color(0xFF35898F),
    context: Context = LocalContext.current.applicationContext
) {
    var dialogOpen by remember { mutableStateOf(false) }

    if (dialogOpen) {
        Dialog(
            onDismissRequest = { dialogOpen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_medal),
                        contentDescription = "Medal",
                        tint = primaryColor,
                        modifier = Modifier.size(150.dp)
                    )

                    Text(
                        text = "Congratulations!! You've won a medal.",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp)
                    )

                    Button(
                        onClick = { Toast.makeText(context, "Shared", Toast.LENGTH_SHORT).show() },
                        modifier = Modifier.padding(top = 20.dp),
                        shape = RoundedCornerShape(percent = 25)
                    ) { Text("Share", fontSize = 18.sp) }
                }
            }
        }
    }
    Button(onClick = { dialogOpen = true }) { Text("Open Dialog") }
}
