package com.example.first_jetcompose.classes

import com.example.first_jetcompose.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val iconFocused: Int
) {
    object Home :
        BottomBarScreen("home", "Home", R.drawable.ic_home, R.drawable.ic_home_focused)

    object Report :
        BottomBarScreen("report", "Report", R.drawable.ic_report, R.drawable.ic_report_focused)

    object Profile :
        BottomBarScreen("profile", "Profile", R.drawable.ic_person, R.drawable.ic_person_focused)
}
