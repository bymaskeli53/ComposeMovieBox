package com.example.composemoviebox

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

        private val _detailState =
            MutableStateFlow<ApiResult<MovieDetailsResponse>>(ApiResult.Loading())
        val detailsState: StateFlow<ApiResult<MovieDetailsResponse>> = _detailState

        fun onAction(action: MovieAction) {
            when (action) {
                is MovieAction.MovieClicked -> {}
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(inputDate: String) : String {
        return formatDateToLocal(inputDate)
        }

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

        fun getFavoriteMovies() {
            viewModelScope.launch {
                val favorites = movieRepository.getFavoriteMovies()
                // Handle favorite movies
            }
        }

        fun fetchMovies() {
            fetchData(
                fetch = { movieRepository.getPopularMovies() },
                state = _state,
            )
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
    }
