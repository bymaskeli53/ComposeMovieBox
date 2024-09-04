package com.example.composemoviebox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
    }
