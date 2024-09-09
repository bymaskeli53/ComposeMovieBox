package com.example.composemoviebox

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
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

    movieItems?.apply {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                LoadingIndicator()
            }

            is LoadState.Error -> {
                val error = loadState.refresh as LoadState.Error
                ErrorMessage(message = error.error.localizedMessage ?: "An error occured")
            }
            is LoadState.NotLoading -> {

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

@Composable
fun LoadingIndicator() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Text(text = message, color = Color.Red, modifier = Modifier.padding(16.dp))
    }

}
