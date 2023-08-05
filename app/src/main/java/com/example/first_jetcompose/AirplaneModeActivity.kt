package com.example.first_jetcompose

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.first_jetcompose.classes.AirplaneModeReceiver
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class AirplaneModeActivity : ComponentActivity() {

    private val airplaneModeReceiver = AirplaneModeReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(airplaneModeReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeReceiver)
    }
}
