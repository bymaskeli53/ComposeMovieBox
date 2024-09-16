package com.example.composemoviebox.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.composemoviebox.model.Movie
import com.example.composemoviebox.util.NetworkConstants.IMAGE_BASE_URL

@Composable
fun MovieGridItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Int) -> Unit,
) {
    Image(
        modifier =
            Modifier
                .padding(8.dp)
                .fillMaxHeight()
                .aspectRatio(0.6f)
                .clickable { movie.id?.let { onClick(it) } }
                .clip(RoundedCornerShape(8.dp)),
        painter =
            rememberAsyncImagePainter(
                model = IMAGE_BASE_URL + movie.poster_path,
            ),
        contentScale = ContentScale.Crop,
        contentDescription = "",
    )
}
