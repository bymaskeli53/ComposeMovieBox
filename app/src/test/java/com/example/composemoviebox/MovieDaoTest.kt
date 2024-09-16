package com.example.composemoviebox

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.composemoviebox.data.FavoriteMovieEntity
import com.example.composemoviebox.data.MovieDao
import com.example.composemoviebox.data.MovieDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {
    private lateinit var db: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        // Create an in-memory version of the database for testing
        db =
            Room
                .inMemoryDatabaseBuilder(
                    context = ApplicationProvider.getApplicationContext(),
                    MovieDatabase::class.java,
                ).build() // For testing only

        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun testAddMovieWorks() =
        runBlocking {
            // Given a new movie
            val movie = FavoriteMovieEntity(1, "Inception", "Best movie ever", "inception.jpg")
            // When inserting the movie
            movieDao.insertFavoriteMovie(movie)

            // Then it should be retrieved from the database
            val movies = movieDao.getAllFavoriteMovies()
            assertEquals(1, movies.size)
        }

    @Test
    fun testDeleteMovie() =
        runBlocking {
            // Given a movie inserted into the database
            val movie = FavoriteMovieEntity(1, "Inception", "Best movie ever", "inception.jpg")
            movieDao.insertFavoriteMovie(movie)

            // When deleting the movie
            movieDao.deleteFavoriteMovie(movie)

            // Then the movie should no longer exist in the database
            assertNull(movie)
        }

//    @Test
//    fun testGetMovieById() = runBlocking {
//        // Given a movie inserted into the database
//        val movie = Movie(id = 1, title = "Inception", isFavorite = true)
//        movieDao.addMovie(movie)
//
//        // When retrieving the movie by its ID
//        val retrievedMovie = movieDao.getMovieById(1)
//
//        // Then it should match the inserted movie
//        assertNotNull(retrievedMovie)
//        assertEquals(movie, retrievedMovie)
//    }
}
