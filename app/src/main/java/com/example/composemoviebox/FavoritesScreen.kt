package com.example.composemoviebox

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Row {
        Text("Favorites Screen")
    }
}
//@Composable
//fun FavoriteScreen (modifier: Modifier = Modifier,movie: List<MovieEntity>) {
//
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(movie) {
//            FavoriteItem(movie = it)
//            Divider(color = Color.Gray, modifier = Modifier.height(ItemSeparatorHeight))
//        }
//    }
