package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.first_jetcompose.classes.Account
import com.example.first_jetcompose.classes.Favorite
import com.example.first_jetcompose.classes.Help
import com.example.first_jetcompose.classes.Home
import com.example.first_jetcompose.classes.MyNetwork
import com.example.first_jetcompose.classes.Notification
import com.example.first_jetcompose.classes.Screens
import com.example.first_jetcompose.classes.screensInHomeFromBottomNav
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import kotlinx.coroutines.launch

class BottomAndDrawerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScaffold()
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    screens: List<Screens.HomeScreens>,
    navController: NavController
) {
    val keyRoute = "android-support-nav:controller:route"
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(keyRoute)
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = "") },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestDisplayName)
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

@Composable
fun AppScaffold() {
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val currentScreen by viewModel.currentScreen.observeAsState()

    var topBar: @Composable () -> Unit = {
        TopBar(buttonIcon = Icons.Filled.Menu,
            title = currentScreen!!.title,
            onButtonClicked = {
                scope.launch {
                    scaffoldState.drawerState.open() // opening the drawer
                }
            })
    }

    if (currentScreen == Screens.DrawerScreens.Help) {
        topBar = {
            TopBar(title = Screens.DrawerScreens.Help.title,
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() })
        }
    }

    val bottomBar: @Composable () -> Unit = { // Check if the current Screen is Home
        if (currentScreen == Screens.DrawerScreens.Home || currentScreen is Screens.HomeScreens) {
            BottomBar(screens = screensInHomeFromBottomNav, navController = navController)
        }
    }

    Scaffold(
        topBar = { topBar() },
        bottomBar = { bottomBar() },
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer { screen ->
                // Close the drawer and put View at top of current
                scope.launch { scaffoldState.drawerState.close() }
                navController.navigate(screen) {
                    popUpTo(navController.graph.startDestDisplayName)
                    launchSingleTop = true
                }
            }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            NavigationHost(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.DrawerScreens.Home.route
    ) {
        composable(Screens.DrawerScreens.Home.route) { Home(viewModel = viewModel) }
        composable(Screens.HomeScreens.Favourite.route) { Favorite(viewModel = viewModel) }
        composable(Screens.HomeScreens.Notification.route) { Notification(viewModel = viewModel) }
        composable(Screens.HomeScreens.Network.route) { MyNetwork(viewModel = viewModel) }
        composable(Screens.DrawerScreens.Account.route) { Account(viewModel = viewModel) }
        composable(Screens.DrawerScreens.Help.route) { Help(viewModel = viewModel) }
    }
}

// To keep a track of the screen we are in
class MainViewModel : ViewModel() {
    private val _currentScreen = MutableLiveData<Screens>(Screens.DrawerScreens.Home)
    val currentScreen: LiveData<Screens> = _currentScreen

    fun setCurrentScreen(screen: Screens) {
        _currentScreen.value = screen
    }

    private val _clickCount = MutableLiveData(0)
    val clickCount: LiveData<Int> = _clickCount

    fun updateClick(value: Int) {
        _clickCount.value = value
    }
}
