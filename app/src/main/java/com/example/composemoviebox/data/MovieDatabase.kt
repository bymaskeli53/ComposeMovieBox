package com.example.composemoviebox.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [FavoriteMovieEntity::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}