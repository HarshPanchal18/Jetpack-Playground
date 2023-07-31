package com.example.first_jetcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlowsActivity : ComponentActivity() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Reference - https://www.youtube.com/playlist?list=PLRKyZvuMYSIPJ84lXQSHMn8P-0J8jW5YT
                    LaunchedEffect(Unit) {
                        GlobalScope.launch(Dispatchers.Main) {
                            val res = flowProducer()
                            delay(6000)
                            res.collect {
                                // `it` will be 30 (as the last state)
                                Log.d("Collecting", it.toString())
                            }
                        }
                        GlobalScope.launch(Dispatchers.Main) {
                            val result = flowStateProducer()
                            Log.d("State producer", result.value.toString())
                        }
                    }
                }
            }
        }
    }

    private fun flowProducer(): Flow<Int> {
        val mutableStateFlow = MutableStateFlow(10)
        GlobalScope.launch {
            delay(2000)
            mutableStateFlow.emit(20)
            delay(2000)
            mutableStateFlow.emit(30)
        }
        return mutableStateFlow
    }

    private fun flowStateProducer(): StateFlow<Int> {
        val mutableStateFlow = MutableStateFlow(10)
        GlobalScope.launch {
            mutableStateFlow.emit(20)
            mutableStateFlow.emit(30)
        }
        return mutableStateFlow
    }
}
