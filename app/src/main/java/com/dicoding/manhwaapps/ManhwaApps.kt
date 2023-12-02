package com.dicoding.manhwaapps

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.dicoding.manhwaapps.navigate.NavList
import com.dicoding.manhwaapps.navigate.Screen
import com.dicoding.manhwaapps.ui.screen.about.AboutMe
import com.dicoding.manhwaapps.ui.screen.detail.DetailScreen
import com.dicoding.manhwaapps.ui.screen.favorite.FavoriteScreen
import com.dicoding.manhwaapps.ui.screen.home.Home

@Composable
fun ManhwaApps(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute != Screen.DetailManhwa.route) {
                BottomBar(navController = navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding).statusBarsPadding()
        ) {
            navigation(
                startDestination = Screen.Home.route,
                route = "main"
            ){
                composable(route = Screen.Home.route) {
                    Home(navigateToDetail = { manhwaId ->
                        navController.navigate(Screen.DetailManhwa.createRoute(manhwaId))
                    })
                }
                composable(route = Screen.Favorite.route) {
                    FavoriteScreen(
                        navigateBack = { navController.navigateUp() }
                    ) { manhwaId ->
                        navController.navigate(Screen.DetailManhwa.createRoute(manhwaId))
                    }
                }
                composable(
                    route = Screen.About.route
                ) {
                    AboutMe(
                        navigateBack = { navController.navigateUp() }
                    )
                }
            }
            composable(
                route = Screen.DetailManhwa.route,
                arguments = listOf(navArgument("manhwaId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("manhwaId") ?: ""
                DetailScreen(
                    manhwaId = id,
                    navigateBack = { navController.navigateUp() }
                )
            }

        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavList(
                title = stringResource(R.string.home),
                icon = Icons.Default.Home,
                page = Screen.Home
            ),
            NavList(
                title = stringResource(R.string.favorite),
                icon = Icons.Default.Favorite,
                page = Screen.Favorite
            ),
            NavList(
                title = stringResource(R.string.about),
                icon = Icons.Default.Person,
                page = Screen.About
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.page.route,
                onClick = {
                    navController.navigate(item.page.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}