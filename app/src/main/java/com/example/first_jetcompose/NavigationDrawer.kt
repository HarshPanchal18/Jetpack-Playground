package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.TopAppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.first_jetcompose.classes.DrawerScreen
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import kotlinx.coroutines.launch

class NavigationDrawer : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenLayout()
                }
            }
        }
    }
}

@Composable
fun ScreenLayout() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        ModalDrawer( // Add a navigation drawer
            drawerState = drawerState, // Controls the open/close state of the drawer
            // Enabling the gesture when the drawer is open so that it can be swiped closed
            gesturesEnabled = drawerState.isOpen, // Used to enable gesture to open/close the drawer
            drawerContent = { // Show the UI of the drawer when it opens.
                Drawer(
                    onDestinationClicked = { route ->
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate(route) {
                            // Pop the backstack till the home destination (not popping home) so that on press of back it comes to home screen.
                            // Our new screen is launched as singleTop so that there is only one instance of it
                            // even if an item is selected multiple times in the drawer.
                            popUpTo(navController.graph.startDestDisplayName)
                            launchSingleTop = true
                        }
                    }
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = DrawerScreen.Home.route
            ) {
                composable(DrawerScreen.Home.route) {
                    HomeScreen(openDrawer = { openDrawer() })
                }
                composable(DrawerScreen.Account.route) {
                    AccountScreen(openDrawer = { openDrawer() })
                }
                composable(DrawerScreen.Help.route) {
                    HelpScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        backgroundColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit // Whenever any item in the drawer is clicked, this callback will be invoked and the corresponding route string will be passed.
) {
    val screens = listOf(DrawerScreen.Home, DrawerScreen.Account, DrawerScreen.Help)

    Column(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.padding(start = 30.dp, top = 48.dp, bottom = 10.dp),
            painter = painterResource(R.drawable.ic_android),
            contentDescription = "App icon",
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        screens.forEach { screen ->
            Text(
                screen.title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDestinationClicked(screen.route)
                    }
                    .padding(vertical = 6.dp)
                    .padding(start = 30.dp)
            )
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun HomeScreen(openDrawer: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        TopBar(title = "Home", buttonIcon = Icons.Filled.Menu, onButtonClicked = { openDrawer() })
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Home Page")
        }
    }
}

@Composable
fun AccountScreen(openDrawer: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        TopBar(
            title = "Account",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() })
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Account Page")
        }
    }
}

@Composable
fun HelpScreen(navController: NavController) {
    Column(Modifier.fillMaxSize()) {
        TopBar(
            title = "Help",
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() })
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Help Page")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDrawer() {
    Drawer(onDestinationClicked = {})
}
