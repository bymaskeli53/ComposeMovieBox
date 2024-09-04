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
            MoviesScreen(navController, onAction = { action ->
                when (action) {
                    is MovieAction.MovieClicked -> {
                        navController.navigate("${Screen.DetailsScreen.route}/${action.movieId}")
                    }
                }
            })
        }
        composable(route = "${Screen.DetailsScreen.route}/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()

            DetailsScreen(movieId = movieId)
        }
    }
}
