package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.first_jetcompose.classes.Screen
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class BottomNavigation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                val navController = rememberNavController()
                val title = remember { mutableStateOf("Account") }
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(title = { Text(text = title.value) },
                                actions = {
                                    IconButton(onClick = {}) {
                                        Icon(Icons.Default.Email, contentDescription = null)
                                    }
                                })
                        },
                        bottomBar = {
                            val items =
                                listOf(Screen.Account, Screen.Date, Screen.Edit, Screen.ThumpUp)
                            BottomNavigation {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val KEY_ROUTE = "android-support-nav:controller:route"
                                val currentRoute =
                                    navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                                items.forEach {
                                    BottomNavigationItem(
                                        icon = { Icon(it.icon, null) },
                                        selected = currentRoute == it.route,
                                        label = { Text(text = it.label) },
                                        onClick = {
                                            navController.popBackStack(
                                                navController.graph.displayName,
                                                false
                                            )
                                            if (currentRoute != it.route) {
                                                navController.navigate(it.route)
                                            }
                                        })
                                }
                            }
                        } // bottomBar
                    ) {
                        it
                        ScreenController(navController = navController, topBarTitle = title)
                    } // Scaffold
                } // Surface
            } // Theme
        }
    }
}

@Composable
fun ScreenController(navController: NavHostController, topBarTitle: MutableState<String>) {
    NavHost(navController = navController, startDestination = "account") {
        composable("account") {
            AccountScreen()
            topBarTitle.value = "Account"
        }
        composable("date") {
            DateScreen()
            topBarTitle.value = "Date"
        }
        composable("edit") {
            EditScreen()
            topBarTitle.value = "Edit"
        }
        composable("thumpup") {
            ThumpUpScreen()
            topBarTitle.value = "Like"
        }
    }
}

@Composable
fun AccountScreen() {
    Text(
        text = "Account Screen",
        style = TextStyle(color = Color.Black, fontSize = 40.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    )
}

@Composable
fun DateScreen() {
    Text(
        text = "Date Screen",
        style = TextStyle(color = Color.LightGray, fontSize = 40.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    )
}

@Composable
fun EditScreen() {
    Text(
        text = "Edit Screen",
        style = TextStyle(color = Color.Magenta, fontSize = 40.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    )
}

@Composable
fun ThumpUpScreen() {
    Text(
        text = "Thump Up",
        style = TextStyle(color = Color.Yellow, fontSize = 40.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    )
}
