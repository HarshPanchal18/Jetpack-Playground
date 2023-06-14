package com.example.first_jetcompose.classes

sealed class DrawerScreen(val title: String, val route: String) {
    object Home : DrawerScreen("Home","home")
    object Account : DrawerScreen("Account","account")
    object Help : DrawerScreen("Help", "help")
}
