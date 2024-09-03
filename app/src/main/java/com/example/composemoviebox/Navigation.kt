package com.example.composemoviebox

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MoviesScreen.route,
    ) {
        composable(route = Screen.MoviesScreen.route) {
            MoviesScreen(navController)
        }
        composable(route = Screen.DetailsScreen.route) {
            DetailsScreen()
        }
    }
}
