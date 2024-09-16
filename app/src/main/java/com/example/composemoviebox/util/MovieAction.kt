package com.example.composemoviebox.util

sealed interface MovieAction {
    data class MovieClicked(
        val movieId: Int,
    ) : MovieAction
}
