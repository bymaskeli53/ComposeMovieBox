package com.example.composemoviebox

data class MovieDetailsResponse(
    val backdrop_path: String? = null,
    val id: Int,
    val overview: String,
    val popularity: Double? = null,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
)
