package com.example.composemoviebox

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<ApiResult<MovieResponse>>

    fun getMovieDetails(movieId: Int): Flow<ApiResult<MovieDetailsResponse>>
}
