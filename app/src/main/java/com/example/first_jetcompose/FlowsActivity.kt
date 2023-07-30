package com.example.first_jetcompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class FlowsActivity : ComponentActivity() {

    private val channel = Channel<Int>()

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

                            flowProducer()
                                .map { it * 2 }
                                .filter { it < 8 } // filter data by the condition
                                .collect { Log.d("Collecting", "$it") } // a terminal operator who starts the flow
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
