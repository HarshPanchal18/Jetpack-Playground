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
                            // Context switching
                            flowProducer()
                                .map {
                                    delay(1000)
                                    it * 2
                                    Log.d("Map thread", "Map thread" + Thread.currentThread().name)
                                }
                                .flowOn(Dispatchers.IO) // will run on IO thread, works on upstream (Decides the context of the above given block)
                                .filter {
                                    delay(500)
                                    Log.d("Filter thread", "Filter thread" + Thread.currentThread().name)
                                    it < 8
                                }
                                .flowOn(Dispatchers.Main) // will run on Main thread
                                .collect { //will run on main thread
                                    Log.d("Collecting", Thread.currentThread().name)
                                } // a terminal operator who starts the flow
                        }
                    }
                }
            }
        }
    }

    private fun flowProducer() = flow<Int> {
        //withContext(Dispatchers.IO) {
        /*
        because of:
        java.lang.IllegalStateException: Flow invariant is violated:
            Flow was collected in [StandaloneCoroutine{Active}@993c81, Dispatchers.Main],
            but emission happened in [DispatchedCoroutine{Active}@e744126, Dispatchers.IO].
            Please refer to 'flow' documentation or use 'flowOn' instead
        */
        val list = listOf(1, 2, 3, 4, 5)
        repeat(list.size) {
            delay(1000)
            Log.d("Emitting", Thread.currentThread().name)
            emit(it)
        }
        //}
    }
}
