package com.example.composemoviebox

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        private val movieRepository: MovieRepository,
    ) : ViewModel() {
        private val _state = MutableStateFlow<ApiResult<MovieResponse>>(ApiResult.Loading())
        val state: StateFlow<ApiResult<MovieResponse>> = _state

    private val _favoriteMovies = MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val favoriteMovies: StateFlow<List<FavoriteMovieEntity>> = _favoriteMovies

        private val _isFavorite = MutableStateFlow(false)
        val isFavorite: StateFlow<Boolean> = _isFavorite

        val listState = LazyListState()

        private val _detailState =
            MutableStateFlow<ApiResult<MovieDetailsResponse>>(ApiResult.Loading())
        val detailsState: StateFlow<ApiResult<MovieDetailsResponse>> = _detailState

        private val _pagingState = MutableStateFlow<PagingData<Movie>?>(null)
        val pagingState: Flow<PagingData<Movie>?> = _pagingState

        val moviesData: Flow<PagingData<Movie>> = movieRepository.getPopularMovies()

        fun onAction(action: MovieAction) {
            when (action) {
                is MovieAction.MovieClicked -> {}
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDate(inputDate: String): String = formatDateToLocal(inputDate)

        fun addFavoriteMovie(movie: FavoriteMovieEntity) {
            viewModelScope.launch {
                movieRepository.addFavoriteMovie(movie)
            }
        }

        fun removeFavoriteMovie(movie: FavoriteMovieEntity) {
            viewModelScope.launch {
                movieRepository.removeFavoriteMovie(movie)
            }
        }

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getFavoriteMovies()
            _favoriteMovies.value = movies // Veriyi state'e aktarıyoruz
        }
    }

        fun fetchMovies() {
            movieRepository
                .getPopularMovies() // Fetch paginated data
                .cachedIn(viewModelScope) // Cache it in the viewModelScope
//                    .collect { pagingData ->
//                        // Collect the flow of PagingData
//                        _pagingState.value = pagingData // Update the state with paginated data
//                   }
        }

        fun fetchMovieDetails(movieId: Int) {
            fetchData(
                fetch = { movieRepository.getMovieDetails(movieId) },
                state = _detailState,
            )
        }

        private fun <T> fetchData(
            fetch: suspend () -> Flow<ApiResult<T>>,
            state: MutableStateFlow<ApiResult<T>>,
        ) {
            viewModelScope.launch {
                fetch().collect { result ->
                    state.value =
                        when (result) {
                            is ApiResult.Success -> ApiResult.Success(result.data)
                            is ApiResult.Error -> ApiResult.Error(result.exception)
                            is ApiResult.Loading -> ApiResult.Loading()
                        }
                }
            }
        }

        fun toggleFavorite(movie: FavoriteMovieEntity) {
            viewModelScope.launch {
                val existingMovie = movieRepository.getFavoriteMovieById(movie.id)
                if (existingMovie == null) {
                    movieRepository.addFavoriteMovie(movie)
                    _isFavorite.value = true
                } else {
                    movieRepository.removeFromFavorites(existingMovie)
                    _isFavorite.value = false
                }
            }
        }

    fun checkFavoriteStatus(movieId: Int) {
        viewModelScope.launch {
            val movieInFavorites = movieRepository.getFavoriteMovieById(movieId)
            _isFavorite.value = movieInFavorites != null
        }
    }
    }
