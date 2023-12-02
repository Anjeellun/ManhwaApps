package com.dicoding.manhwaapps.navigate

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailManhwa : Screen("home/{manhwaId}") {
        fun createRoute(manhwaId: String) = "home/$manhwaId"
    }
}