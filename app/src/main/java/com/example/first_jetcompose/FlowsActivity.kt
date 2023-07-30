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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

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
                    LaunchedEffect(Unit) {
                        GlobalScope.launch {
                            val data =
                                flowProducer().toList() // consume the emitted flow elements into the list
                            Log.d("Values in list", data.toString())

                            val time = measureTimeMillis {
                                flowProducer()
                                    .buffer(3) // Adding buffer process to consume data swiftly with less time
                                    .collect {
                                        delay(1500)
                                        Log.d("Collecting", "$it")
                                    } // a terminal operator who starts the flow
                            }
                            Log.d("Time taken",time.toString())
                        }
                    }
                }
            }
        }
    }

    private fun flowProducer() = flow<Int> {
        val list = listOf(1, 2, 3, 4, 5)
        repeat(list.size) {
            delay(1000)
            emit(it)
        }
    }
}
