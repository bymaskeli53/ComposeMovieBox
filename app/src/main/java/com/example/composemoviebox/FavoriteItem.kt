package com.example.composemoviebox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun FavoriteItem(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text(
            text = movie.title ?: "No title",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        Text(
            text = movie.overview ?: "No overview",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}