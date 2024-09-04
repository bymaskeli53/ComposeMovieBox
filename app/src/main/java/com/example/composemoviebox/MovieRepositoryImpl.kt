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
    ) : MovieRepository {
        fun fetchData(): Flow<ApiResult<MovieResponse>> =
            flow {
                emit(ApiResult.Loading())
                try {
                    val data = movieService.getPopularMovies()
                    emit(ApiResult.Success(data))
                } catch (e: Exception) {
                    emit(ApiResult.Error(e))
                }
            }

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
    }
