package com.example.composemoviebox

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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



    // Grid ve Linear View arası geçiş için state'i rememberSaveable ile kalıcı hale getiriyoruz
    var isGrid by rememberSaveable { mutableStateOf(false) }

    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    LaunchedEffect(Unit) {
        viewModel.fetchMovies()
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        // Görünüm değiştirme butonu
        Button(
            onClick = { isGrid = !isGrid },
            modifier =
            Modifier
                .width(128.dp)
                .padding(16.dp),
        ) {
            Image(
                painter = painterResource(id = if (isGrid) R.drawable.grid else R.drawable.linear),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        movieItems?.apply {
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingIndicator()
                }

                is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    ErrorMessage(message = error.error.localizedMessage ?: "An error occurred")
                }

                is LoadState.NotLoading -> {
                    if (isGrid) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            movieItems?.let {
                                items(movieItems.itemCount) { index ->
                                    val movie = movieItems[index]

                                    if (movie != null) {
                                        MovieGridItem(movie = movie) {
                                            onAction(MovieAction.MovieClicked(it))
                                        }
                                    }
                                }
                            }
                           item{
                               if (loadState.append is LoadState.Loading) {
                                LoadingIndicator()
                                }
                           }
                        }
                    } else {
                        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                            movieItems?.let {
                                items(movieItems.itemCount) { index ->
                                    val movie = movieItems[index]

                                    if (movie != null) {
                                        MovieListItem(movie = movie) {
                                            onAction(MovieAction.MovieClicked(it))
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }

        // LazyColumn veya LazyVerticalGrid'i state'e göre göster



    }
}

@Composable
fun LoadingIndicator() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = message, color = Color.Red, modifier = Modifier.padding(16.dp))
    }
}
