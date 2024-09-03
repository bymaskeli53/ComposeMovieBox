package com.example.composemoviebox

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun MoviesScreen(navController: NavController) {
    LazyColumn(modifier = Modifier.background(color = Color.DarkGray)) {
        items(10) {
        }
    }
}
