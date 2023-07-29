package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class NestedNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = IntroNav.INTRO_ROUTE
                    ) {
                        introGraph(navController)
                        mainGraph(navController)
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.introGraph(navController: NavController) {
    navigation(startDestination = IntroNav.INTRO_WELCOME, route = IntroNav.INTRO_ROUTE) {
        composable(IntroNav.INTRO_WELCOME) { WelcomeScreen(navController) }
        composable(IntroNav.INTRO_MOTIVATION) { MotivationScreen(navController) }
        composable(IntroNav.INTRO_RECOMMENDATION) { RecommendScreen(navController) }
    }

}

object IntroNav {
    const val INTRO_ROUTE = "intro"
    const val INTRO_WELCOME = "welcome"
    const val INTRO_MOTIVATION = "motivation"
    const val INTRO_RECOMMENDATION = "recommend"
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(startDestination = MainNav.MAIN_HOME, route = MainNav.MAIN_ROUTE) {
        composable(MainNav.MAIN_HOME) { HomeScreen(navController) }
        composable(MainNav.MAIN_SETTINGS) { SettingsScreen(navController) }
        composable(MainNav.MAIN_ABOUT) { AboutScreen(navController) }
    }
}

object MainNav {
    const val MAIN_ROUTE = "main"
    const val MAIN_HOME = "home"
    const val MAIN_SETTINGS = "settings"
    const val MAIN_ABOUT = "about"
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome")

        Button(onClick = {
            navController.navigate(IntroNav.INTRO_MOTIVATION)
        }) { Text("Go to motivation") }

        Button(onClick = {
            navController.popBackStack()
        }) { Text("Go back") }
    }
}

@Composable
fun MotivationScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Motivation")

        Button(onClick = {
            navController.navigate(IntroNav.INTRO_RECOMMENDATION)
        }) { Text("Go to recommendation") }

        Button(onClick = {
            navController.popBackStack()
        }) { Text("Go back") }
    }
}

@Composable
fun RecommendScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Recommend")

        Button(onClick = {
            navController.navigate(MainNav.MAIN_ROUTE) {
                popUpTo(IntroNav.INTRO_ROUTE)
            }
        }) { Text("Go to Main") }

        Button(onClick = {
            navController.popBackStack()
        }) { Text("Go back") }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home")

        Button(onClick = {
            navController.navigate(MainNav.MAIN_SETTINGS)
        }) { Text("Go to settings") }

        Button(onClick = {
            navController.popBackStack()
        }) { Text("Go back") }
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings")

        Button(onClick = {
            navController.navigate(MainNav.MAIN_ABOUT)
        }) { Text("Go to about") }

        Button(onClick = {
            navController.popBackStack()
        }) { Text("Go back") }
    }
}

@Composable
fun AboutScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("About")

        Button(onClick = {
            navController.navigate(IntroNav.INTRO_ROUTE) {
                popUpTo(MainNav.MAIN_ROUTE)
            }
        }) { Text("Go to Welcome") }

        Button(onClick = {
            navController.popBackStack()
        }) { Text("Go back") }
    }
}
