package com.example.composemoviebox.data

import androidx.paging.PagingData
import com.example.composemoviebox.model.Movie
import com.example.composemoviebox.model.MovieDetailsResponse
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
