package com.example.composemoviebox

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MoviesScreen(
    navController: NavController = rememberNavController(),
    viewModel: MovieViewModel = hiltViewModel(),
    onAction: (MovieAction) -> Unit = {},
) {

    val movieItems = viewModel.moviesData.collectAsLazyPagingItems()

    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    LaunchedEffect(Unit) {
        viewModel.fetchMovies()
    }
   // val moviesState by viewModel.state.collectAsState()

    LazyColumn {
        movieItems?.let {
            items(movieItems.itemCount){index ->
                val movie = movieItems[index]

                if (movie != null) {
                    MovieListItem(movie = movie) {
                        onAction(MovieAction.MovieClicked(it))
                        
                    }
                }
            }
        }
    }

//    when (moviesState) {
//        is ApiResult.Loading -> {
//            // Loading Progres
//            CircularProgressIndicator()
//        }
//
//        is ApiResult.Success -> {
//            val movies = (moviesState as ApiResult.Success).data.results
//            when (onAction) {
//
//            }
//            LazyColumn(state = listState) {
//                items(count = movieItems.itemCount) { index ->
//                    val movie = movieItems[index]
//
//                    if (movie != null) {
//                        MovieListItem(movie = movie){
//                            onAction(MovieAction.MovieClicked(it))
//
//                        }
//                    }
//                }
//            }
//        }
//
//        is ApiResult.Error -> {
//            Toast
//                .makeText(
//                    LocalContext.current,
//                    "Error ${(moviesState as ApiResult.Error).exception.message}",
//                    Toast.LENGTH_SHORT,
//                ).show()
//        }
//    }
}
