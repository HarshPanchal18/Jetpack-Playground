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
import kotlinx.coroutines.flow.flow
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
                    LaunchedEffect(Unit) {
                        GlobalScope.launch(Dispatchers.Main) {
                            val res = flowProducer()
                            res.collect {
                                Log.d("Collecting on flow1", it.toString())
                            }
                        }
                        GlobalScope.launch(Dispatchers.Main) {
                            val res = flowProducer()
                            delay(2500)
                            res.collect {
                                Log.d("Collecting on flow2", it.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun flowProducer(): Flow<Int> {
        val mutableSharedFlow = MutableSharedFlow<Int>(replay = 2) // Replay = capture the last N-missed value from the flow
        GlobalScope.launch {
            val list = listOf(1, 2, 3, 4, 5)
            list.forEach {
                mutableSharedFlow.emit(it)
                delay(1000)
            }
        }
        return mutableSharedFlow
    }
}
