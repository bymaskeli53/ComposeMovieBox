package com.example.composemoviebox

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(
        private val movieService: MovieService,
        private val movieDao: MovieDao,
    ) : MovieRepository {
        override fun getPopularMovies(): Flow<PagingData<Movie>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    MoviePagingSource(
                        apiKey = BuildConfig.API_KEY,
                        apiService = movieService,
                    )
                },
            ).flow

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

    override suspend fun getFavoriteMovies(): List<FavoriteMovieEntity> {
        return movieDao.getAllFavoriteMovies()
    }


    override suspend fun getFavoriteMovieById(movieId: Int): FavoriteMovieEntity? {
            return movieDao.getMovieById(movieId)
    }

    override suspend fun removeFromFavorites(movie: FavoriteMovieEntity) {
        movieDao.deleteFavoriteMovie(movie)
    }
}
