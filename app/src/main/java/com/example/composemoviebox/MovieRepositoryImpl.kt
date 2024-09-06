package com.example.composemoviebox

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(
        private val movieService: MovieService,
        private val movieDao: MovieDao
    ) : MovieRepository {

        override fun getPopularMovies(): Flow<ApiResult<MovieResponse>> =
            flow {
                emit(ApiResult.Loading())
                try {
                    val movies = movieService.getPopularMovies()
                    emit(ApiResult.Success(movies))
                } catch (e: Exception) {
                    emit(ApiResult.Error(e))
                }
            }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(movieId: Int): Flow<ApiResult<MovieDetailsResponse>> =
        flow {
            emit(ApiResult.Loading())
            try {
                val movieDetails = movieService.getMovieDetails(movieId)
                emit(ApiResult.Success(movieDetails))
            } catch (e: Exception) {
                emit(ApiResult.Error(e))
        }




        }.flowOn(Dispatchers.IO)

    override suspend fun addFavoriteMovie(movie: FavoriteMovieEntity) {
        movieDao.insertFavoriteMovie(movie)
    }

    override suspend fun removeFavoriteMovie(movie: FavoriteMovieEntity) {
        movieDao.deleteFavoriteMovie(movie)
    }

    override suspend fun getFavoriteMovies() {
        movieDao.getAllFavoriteMovies()
    }
}
