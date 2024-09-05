package com.example.composemoviebox

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
