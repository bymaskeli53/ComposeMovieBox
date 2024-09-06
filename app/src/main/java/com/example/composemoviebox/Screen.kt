package com.example.composemoviebox

sealed class Screen(
    val route: String,
) {
    object MoviesScreen : Screen("movies_screen")

    object DetailsScreen : Screen("details_screen")
    object FavoritesScreen : Screen("favorites_screen")
}
