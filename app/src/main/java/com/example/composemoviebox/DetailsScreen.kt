package com.example.composemoviebox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.composemoviebox.NetworkConstants.IMAGE_BASE_URL
import com.example.composemoviebox.ui.theme.bg_details

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    movieId: Int?,
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        if (movieId != null) {
            viewModel.fetchMovieDetails(movieId)
        }
    }
    val moviesState by viewModel.detailsState.collectAsState()

    when (moviesState) {
        is ApiResult.Success -> {
            val movie = (moviesState as ApiResult.Success).data


            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(bg_details),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = IMAGE_BASE_URL + movie.backdrop_path),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = movie.title ?: "No Title",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = movie.release_date ?: "No Date",
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    RatingBar(rating = movie.vote_average.toString())
                    Spacer(modifier = Modifier.height(16.dp))

                    ExpandableText(text = movie.overview,modifier = Modifier.padding(12.dp))
                }
            }
        }

        is ApiResult.Error -> {
            Text(text = "Error")
        }

        is ApiResult.Loading -> {
            Text(text = "Loading")
        }
    }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(scrollState)
//            .background(Color.White)
//    ) {
//        // Background Image
//        Image(
//            painter = rememberAsyncImagePainter(model = IMAGE_BASE_URL + movie.backdrop_path),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//
//        )
//
//    }
}
