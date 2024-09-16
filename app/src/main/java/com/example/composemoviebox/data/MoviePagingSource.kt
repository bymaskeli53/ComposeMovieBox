package com.example.composemoviebox.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.composemoviebox.model.Movie

class MoviePagingSource(
    private val apiService: MovieService,
    private val apiKey: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val response = apiService.getPopularMovies(apiKey,nextPage)
            LoadResult.Page(
                data = response.results, // List<Movie>
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.results.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
