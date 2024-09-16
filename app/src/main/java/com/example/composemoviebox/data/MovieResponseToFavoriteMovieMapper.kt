package com.example.composemoviebox.data

import com.example.composemoviebox.model.MovieDetailsResponse

fun MovieDetailsResponse.toFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = this.id ?: 0, // id null olabilir, bu yüzden varsayılan olarak 0 veriyoruz
        title = this.title ?: "No Title", // null durumunu kontrol edip varsayılan değer veriyoruz
        overview = this.overview ?: "No Overview", // null durumu için varsayılan değer
        posterPath = this.poster_path ?: "" // poster path null ise boş string
    )
}