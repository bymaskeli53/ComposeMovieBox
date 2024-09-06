package com.example.composemoviebox

import androidx.annotation.DrawableRes

sealed class Screen(
    val route: String,
    @DrawableRes val icon: Int? = null,
) {
    object MoviesScreen : Screen("movies_screen",R.drawable.ic_movie)

    object DetailsScreen : Screen("details_screen")
    object FavoritesScreen : Screen("favorites_screen",R.drawable.ic_favorite)
}
