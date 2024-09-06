package com.example.composemoviebox

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    private lateinit var fakeMovieRepository: FakeMovieRepository
    private lateinit var movieViewModel: MovieViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        fakeMovieRepository = FakeMovieRepository()
        movieViewModel = MovieViewModel(fakeMovieRepository)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `fetchMovies returns success`() = runTest {
        // Arrange
        val movieResponse = MovieResponse(results = listOf(
            Movie(id = 1, overview = "Test movie 1", poster_path = "path1", title = "Movie 1", vote_average = 7.0, release_date = "2023-09-04"),
            Movie(id = 2, overview = "Test movie 2", poster_path = "path2", title = "Movie 2", vote_average = 8.0, release_date = "2023-09-05")
        ))
        fakeMovieRepository.setMoviesResponse(movieResponse)

        // Act
        movieViewModel.fetchMovies()

        // Assert
        movieViewModel.state.test {
           // assertEquals(ApiResult.Loading<MovieResponse>(), awaitItem())
            assertEquals(ApiResult.Success(movieResponse), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `fetchMoviesDetails returns success`() = runTest {
        // Arrange
        val movieResponse = MovieDetailsResponse(
           id = 2, overview = "Test movie 2", poster_path = "path2", title = "Movie 2", vote_average = 8.0, release_date = "2023-09-05"
        )
        fakeMovieRepository.setMovieDetailsResponse(movieResponse)

        // Act
        movieViewModel.fetchMovieDetails(533535)

        // Assert
        movieViewModel.detailsState.test {
            // assertEquals(ApiResult.Loading<MovieResponse>(), awaitItem())
            assertEquals(ApiResult.Success(movieResponse), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `fetchMovies returns error`() = runTest {
        // Arrange
        val exception = RuntimeException("Error occurred")
        fakeMovieRepository.setShouldReturnError(true)

        // Act
        movieViewModel.fetchMovies()

        // Assert
        movieViewModel.state.test {
            //assertEquals(ApiResult.Loading<MovieResponse>(), awaitItem())
           // assertEquals(ApiResult.Error<MovieResponse>(exception = exception, data = null),awaitItem())
            val result = awaitItem()
            assert(result is ApiResult.Error && result.exception.message == exception.message)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @OptIn(ExperimentalTime::class)
    @Test
    fun `fetchMovieDetails returns error`() = runTest {
        // Arrange
        val exception = RuntimeException("Error occurred")
        fakeMovieRepository.setShouldReturnError(true)

        // Act
        movieViewModel.fetchMovieDetails(533535)

        // Assert
        movieViewModel.detailsState.test {
            //assertEquals(ApiResult.Loading<MovieResponse>(), awaitItem())
            // assertEquals(ApiResult.Error<MovieResponse>(exception = exception, data = null),awaitItem())
            val result = awaitItem()
            assert(result is ApiResult.Error && result.exception.message == exception.message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}


