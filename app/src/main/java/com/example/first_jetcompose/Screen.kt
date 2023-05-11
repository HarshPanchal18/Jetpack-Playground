package com.example.first_jetcompose

sealed class Screen(val route: String) {
    object Home: Screen("Home")
    object Detail: Screen("Detail")
}
