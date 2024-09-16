package com.example.composemoviebox.model

data class MovieResponse(
    val results: List<Movie>,
    val total_pages: Int? = null,
    val total_results: Int? = null,
)
