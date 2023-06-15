package com.example.first_jetcompose.classes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.first_jetcompose.MainViewModel

sealed class Screens(val route: String, val title: String) {
    sealed class HomeScreens(route: String, title: String, val icon: ImageVector) :
        Screens(route, title) {
        object Favourite : HomeScreens("favourite", "Favourite", Icons.Filled.Favorite)
        object Notification :
            HomeScreens("notification", "Notification", Icons.Filled.Notifications)
        object Network : HomeScreens("network", "Network", Icons.Filled.Person)
    }

    sealed class DrawerScreens(route: String, title: String) : Screens(route, title) {
        object Home : DrawerScreens("home", "Home")
        object Account : DrawerScreens("account", "Account")
        object Help : DrawerScreens("help", "Help")
    }
}

val screensInHomeFromBottomNav = listOf(
    Screens.HomeScreens.Favourite,
    Screens.HomeScreens.Notification,
    Screens.HomeScreens.Network,
)

@Composable
fun Home(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.DrawerScreens.Home)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun Favorite(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.HomeScreens.Favourite)
    val clickCount by viewModel.clickCount.observeAsState()
    // var click by rememberSaveable { mutableIntStateOf(0) }
    var click = clickCount ?: 0
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Favorite.", style = MaterialTheme.typography.bodyMedium)
        Button(
            onClick = {
                click++
                viewModel.updateClick(click)
            }
        ) {
            Text("Clicked this time: $click")
        }
    }
}

@Composable
fun Notification(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.HomeScreens.Notification)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Notification", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun MyNetwork(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.HomeScreens.Network)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "MyNetwork", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun Account(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.DrawerScreens.Account)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Account", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun Help(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.DrawerScreens.Help)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Help", style = MaterialTheme.typography.bodyMedium)
    }
}
