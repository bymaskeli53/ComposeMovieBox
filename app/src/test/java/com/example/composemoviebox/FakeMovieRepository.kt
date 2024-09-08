package com.example.composemoviebox

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository : MovieRepository {
    private var shouldReturnError = false
    private var moviesResponse: MovieResponse? = null
    private var movieDetailsResponse: MovieDetailsResponse? = null

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun setMoviesResponse(response: MovieResponse) {
        moviesResponse = response
    }

    fun setMovieDetailsResponse(response: MovieDetailsResponse) {
            movieDetailsResponse = response
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

    override fun getMovieDetails(movieId: Int): Flow<ApiResult<MovieDetailsResponse>> = flow {
        emit(ApiResult.Loading())

        if (shouldReturnError) {
            emit(ApiResult.Error(RuntimeException("Error occurred")))
        } else {
            movieDetailsResponse?.let {
                emit(ApiResult.Success(it))
            }
        }
    }

    override suspend fun addFavoriteMovie(movie: FavoriteMovieEntity) {

    }

    override suspend fun removeFavoriteMovie(movie: FavoriteMovieEntity) {

    }

    override suspend fun getFavoriteMovies() {

    }


}

