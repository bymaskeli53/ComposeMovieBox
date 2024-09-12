package com.example.composemoviebox

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchFavoriteMovies()


    }

    LazyColumn {
        items(favoriteMovies) {
            FavoriteItem(movie = it)
        }
    }
}
// @Composable
// fun FavoriteScreen (modifier: Modifier = Modifier,movie: List<MovieEntity>) {
//
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(movie) {
//            FavoriteItem(movie = it)
//            Divider(color = Color.Gray, modifier = Modifier.height(ItemSeparatorHeight))
//        }
//    }
