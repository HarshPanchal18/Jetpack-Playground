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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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
                        CoroutineScope(Dispatchers.Main).launch {
                            getUserNames().forEach {
                                Log.d("USER", it)
                            }
                        }

                        //val job = GlobalScope.launch {
                        GlobalScope.launch {
                            val data: Flow<Int> = flowProducer()
                            data
                                .onStart {
                                    emit(-1) // emitting values on starting of collecting flow
                                    Log.d("onStart", "Started emitting")
                                }
                                .onEach { Log.d("onEach", "Emitting $it") } // skipping the "Emitting - 5" because the completion is below the .onEach{}
                                .onCompletion {
                                    emit(5) // emitting values on completion of collecting flow
                                    Log.d("onCompletion", "Completed emitting")
                                }
                                .collect { Log.d("Flow - 1", it.toString()) }
                        }

                        // Multiple consumers for the same producer
                        /*GlobalScope.launch {
                            val data: Flow<Int> = flowProducer()
                            delay(2500)
                            data.collect {
                                Log.d("Flow - 2", it.toString())
                            }
                        }*/

                        /*GlobalScope.launch {
                            delay(3500)
                            job.cancel()
                        }*/
                    }
                    // Channels
                    channelProducer()
                    channelReceiver()
                }
            }
        }
    }

    private suspend fun getUserNames(): List<String> {
        val list = mutableListOf<String>()
        list.add(getUser(1))
        list.add(getUser(2))
        list.add(getUser(3))
        Toast.makeText(this, "Fetched and Sent", Toast.LENGTH_SHORT).show()
        return list
    }

    private suspend fun getUser(id: Int): String {
        delay(1000)
        return "User$id"
    }

    private fun channelProducer() {
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            channel.send(2)
            channel.send(3)
        }
    }

    private fun channelReceiver() {
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(
                "Channels",
                channel.receive().toString() + channel.receive().toString() + channel.receive()
                    .toString()
            )
        }
    }

    private fun flowProducer() = flow<Int> {
        val list = listOf(1, 2, 3, 4, 5)
        //list.forEach {
        repeat(list.size) {
            delay(1000)
            emit(it)
        }
    }
}
