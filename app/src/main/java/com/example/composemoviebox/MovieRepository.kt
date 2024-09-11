package com.example.composemoviebox

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getMovieDetails(movieId: Int): Flow<ApiResult<MovieDetailsResponse>>

    suspend fun addFavoriteMovie(movie: FavoriteMovieEntity)

    suspend fun removeFavoriteMovie(movie: FavoriteMovieEntity)

    suspend fun getFavoriteMovies() : List<FavoriteMovieEntity>

    suspend fun getFavoriteMovieById(movieId: Int): FavoriteMovieEntity?

    suspend fun removeFromFavorites(movie: FavoriteMovieEntity)
}
