package com.example.composemoviebox

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailsScreen(modifier: Modifier = Modifier,movieId: Int?) {
    Row {
        Text(text = "Movie ID: $movieId")
    }

}
