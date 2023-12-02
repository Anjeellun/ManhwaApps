package com.dicoding.manhwaapps.navigate

import androidx.compose.ui.graphics.vector.ImageVector

data class NavList(
    val title: String,
    val icon: ImageVector,
    val page: Screen
)