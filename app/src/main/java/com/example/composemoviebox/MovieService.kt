package com.example.composemoviebox

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService  {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
    ): MovieResponse
}
