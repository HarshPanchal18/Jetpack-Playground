package com.example.first_jetcompose.classes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home: Screen("Home","Home", Icons.Default.Home)
    object Detail: Screen("Detail","Detail", Icons.Default.Details)
    object Account: Screen("account","Account", Icons.Default.AccountBox)
    object Date: Screen("date","Date", Icons.Default.DateRange)
    object Edit: Screen("edit","Edit", Icons.Default.Edit)
    object ThumpUp: Screen("thumpup","Like", Icons.Default.ThumbUp)
}
