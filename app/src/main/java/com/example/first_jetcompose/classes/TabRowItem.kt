package com.example.first_jetcompose.classes

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TabRowItem(
    val title: String,
    val icon: ImageVector,
    val tint: Color,
    val screen: @Composable () -> Unit
)
