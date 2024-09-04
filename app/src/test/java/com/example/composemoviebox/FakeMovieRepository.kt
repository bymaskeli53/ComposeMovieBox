package com.example.composemoviebox

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository : MovieRepository {
    private var shouldReturnError = false
    private var moviesResponse: MovieResponse? = null

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun setMoviesResponse(response: MovieResponse) {
        moviesResponse = response
    }

    override fun getPopularMovies(): Flow<ApiResult<MovieResponse>> = flow {
        emit(ApiResult.Loading())

        if (shouldReturnError) {
            emit(ApiResult.Error(RuntimeException("Error occurred")))
        } else {
            moviesResponse?.let {
                emit(ApiResult.Success(it))
            }
        }
    }
}

