package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class NavigationWoSealedClassActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Navigation()
                    }
                }
            }
        }
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { Home(navController) }
            composable("second/{userId}/{age}",
                arguments = listOf(
                    navArgument("userId") { type = NavType.StringType },
                    navArgument("age") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val userID = backStackEntry.arguments?.getString("userId")
                val age = backStackEntry.arguments?.getInt("age")
                SecondScreen(navController, userID.toString(), age)
            }
        }
    }

    @Composable
    private fun Home(navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val arg0 = remember { mutableStateOf("") }
            val arg1 = remember { mutableStateOf("") }

            TextField(
                value = arg0.value, onValueChange = { arg0.value = it },
                label = { Text("Name") },
                modifier = Modifier.padding(top = 10.dp)
            )
            TextField(
                value = arg1.value, onValueChange = { arg1.value = it },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(top = 10.dp)
            )
            Button(
                onClick = {
                    navController.navigate("second/${arg0.value}/${arg1.value}") {
                        popUpTo("home")
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                enabled = arg0.value.isNotEmpty() && arg1.value.isNotEmpty(),
                modifier = Modifier.padding(top = 10.dp)
            ) { Text("Press for second screen") }
        }
    }

    @Composable
    private fun SecondScreen(
        navController: NavHostController,
        argument1: String,
        argument2: Int?
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = argument1)
            Text(text = argument2.toString())
            Button(onClick = {
                navController.popBackStack()
            }) { Text("Press for previous screen") }
        }
    }
}
