package com.example.composemoviebox.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composemoviebox.util.MovieAction
import com.example.composemoviebox.ui.chat.ChatScreen
import com.example.composemoviebox.ui.favorites.FavoritesScreen
import com.example.composemoviebox.ui.details.DetailsScreen
import com.example.composemoviebox.ui.movie.MoviesScreen

@Composable
fun Navigation(navController : NavHostController,modifier : Modifier = Modifier) {
  //  val navController = rememberNavController()
    


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
        composable(route = Screen.FavoritesScreen.route){
            FavoritesScreen()
        }

        composable(route = Screen.ChatScreen.route){
            ChatScreen()
        }
    }
}
