package com.example.first_jetcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class OtpTextField : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) { OtpBox() }
            }
        }
    }
}

@Composable
fun OtpBox() {
    val context = LocalContext.current
    var otpValue by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        BasicTextField(value = otpValue,
            onValueChange = {
                if (it.length <= 6) otpValue = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(6) { index ->
                        val char = when {
                            index >= otpValue.length -> ""
                            else -> otpValue[index].toString()
                        }
                        Text(
                            text = char,
                            modifier = Modifier
                                .width(40.dp)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                .padding(2.dp),
                            style = MaterialTheme.typography.h4,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                }
            })
        Button(
            onClick = {
                if (otpValue.length != 6) Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT)
                    .show()
                else Toast.makeText(context, "Verified: $otpValue", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.CenterHorizontally)
        ) { Text("Verify") }
    }
}
