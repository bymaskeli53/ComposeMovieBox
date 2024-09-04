package com.example.composemoviebox

sealed interface MovieAction {
    data class MovieClicked(
        val movieId: Int,
    ) : MovieAction
}
