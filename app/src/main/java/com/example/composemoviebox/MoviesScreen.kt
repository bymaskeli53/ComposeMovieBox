package com.example.composemoviebox

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MoviesScreen(navController: NavController = rememberNavController(), viewModel: MovieViewModel = hiltViewModel()) {
   val moviesState by viewModel.state.collectAsState()

    when (moviesState) {
        is ApiResult.Loading -> {
            // Loading Progres
            CircularProgressIndicator()
        }
        is ApiResult.Success -> {
            val movies = (moviesState as ApiResult.Success).data.results
            LazyColumn {
                items(movies){
                    MovieListItem(movie = it)
                }
            }
        }
        is ApiResult.Error -> {
            Toast.makeText(LocalContext.current, "Error ${(moviesState as ApiResult.Error).exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
