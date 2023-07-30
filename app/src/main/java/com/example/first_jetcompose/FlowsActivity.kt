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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                        GlobalScope.launch(Dispatchers.Main) {
                            try {
                                flowProducer()
                                    .collect { //will run on main thread
                                        Log.d("Collecting", Thread.currentThread().name)
                                        //throw Exception("Error in collector")
                                    } // a terminal operator who starts the flow
                            } catch (e: Exception) {
                                Log.d("Calling exception from consumer", e.message.toString())
                            }
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
            Log.d("Emitting", Thread.currentThread().name)
            emit(it)
            throw Exception("Error in emitter")
        }
    }.catch {
        // Handle the exceptions of producer block instead of passing exception to try..catch block of the consumer
        Log.d("Emitter catch block", it.message.toString())
        emit(-1)
    }
}
