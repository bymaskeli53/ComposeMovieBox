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

    private val _detailState = MutableStateFlow<ApiResult<MovieDetailsResponse>>(ApiResult.Loading())
    val detailsState: StateFlow<ApiResult<MovieDetailsResponse>> = _detailState

    fun onAction(action: MovieAction) {
        when (action) {
            is MovieAction.MovieClicked -> { }
            }
        }

        fun fetchMovies() {
            viewModelScope.launch {
                movieRepository
                    .getPopularMovies()
                    .collect { result ->
                        when (result) {
                            is ApiResult.Success -> {
                                _state.value = ApiResult.Success(result.data)
                            }
                            is ApiResult.Error -> {
                                _state.value = ApiResult.Error(result.exception)
                            }
                            is ApiResult.Loading -> {
                                _state.value = ApiResult.Loading()
                            }
                        }
                    }
            }
        }

    fun fetchMovieDetails(movieId: Int) {
            viewModelScope.launch {
                movieRepository.getMovieDetails(movieId)
                    .collect{ result ->
                        when (result) {
                            is ApiResult.Success -> {
                                _detailState.value = ApiResult.Success(result.data)
                            }
                            is ApiResult.Error -> {
                                _detailState.value = ApiResult.Error(result.exception)
                            }
                            is ApiResult.Loading -> {
                                _detailState.value = ApiResult.Loading()
                            }
                        }

                    }
            }
        }

    private fun <T> fetchData(
        fetch: suspend () -> Flow<ApiResult<T>>,
        state: MutableStateFlow<ApiResult<T>>
    ) {
        viewModelScope.launch {
            fetch().collect { result ->
                state.value = when (result) {
                    is ApiResult.Success -> ApiResult.Success(result.data)
                    is ApiResult.Error -> ApiResult.Error(result.exception)
                    is ApiResult.Loading -> ApiResult.Loading()
                }
            }
        }
    }
    }
