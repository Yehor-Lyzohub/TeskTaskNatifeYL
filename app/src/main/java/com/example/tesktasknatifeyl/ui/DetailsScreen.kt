package com.example.tesktasknatifeyl.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tesktasknatifeyl.R
import com.example.tesktasknatifeyl.coil.CoilImageLoader

@Composable
fun DetailsScreen(url: String) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        RenderGif(url)
    }
}

@Composable
fun RenderGif(url: String) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .aspectRatio(1f),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = "Gif",
            imageLoader = CoilImageLoader(LocalContext.current).customImageLoader,
            error = painterResource(R.drawable.ic_broken_img),
            placeholder = painterResource(R.drawable.ic_loading_img),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}