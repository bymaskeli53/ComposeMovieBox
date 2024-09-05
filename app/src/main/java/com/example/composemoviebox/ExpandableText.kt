package com.example.composemoviebox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun ExpandableText(text: String,modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            fontSize = 16.sp,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,

        )

        Text(
            text = if (isExpanded) "Read Less" else "Read More",
            color = Color.LightGray,
            modifier = Modifier.clickable { isExpanded = !isExpanded }
        )
    }
}