package com.example.composemoviebox

data class MovieResponse(
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int,
)
