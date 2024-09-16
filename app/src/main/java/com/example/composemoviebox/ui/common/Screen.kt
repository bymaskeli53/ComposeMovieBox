package com.example.composemoviebox.ui.common

import androidx.annotation.DrawableRes
import com.example.composemoviebox.R

sealed class Screen(
    val route: String,
    @DrawableRes val icon: Int? = null,
) {
    object MoviesScreen : Screen("Movies", R.drawable.ic_movie)

    object DetailsScreen : Screen("Details")
    object FavoritesScreen : Screen("Favorites", R.drawable.ic_favorite)

    object ChatScreen : Screen("Chat", R.drawable.advise)
}
