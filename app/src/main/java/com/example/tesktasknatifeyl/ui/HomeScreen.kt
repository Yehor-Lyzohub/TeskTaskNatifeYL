package com.example.tesktasknatifeyl.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.tesktasknatifeyl.R
import com.example.tesktasknatifeyl.data.Data
import com.example.tesktasknatifeyl.data.GiphyData
import com.example.tesktasknatifeyl.viewmodel.GifViewModel
import com.example.tesktasknatifeyl.viewmodel.ItemUiState

@Composable
fun HomeScreen() {

    val gifViewModel: GifViewModel = viewModel()
    val itemUiState = gifViewModel.itemUiState

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when (itemUiState) {
            is ItemUiState.Loading -> LoadingScreen()
            is ItemUiState.Success -> RenderGifList(itemUiState.items)
            is ItemUiState.Error -> ErrorScreen()
        }
    }
}

@Composable
fun RenderGifList(gifs: GiphyData) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(items = gifs.data, key = { gif -> gif.id }) { gif ->
            GifCard(gif)
        }
    }
}

@Composable
fun GifCard(gifs: Data) {
    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxSize()
        .aspectRatio(1f),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .respectCacheHeaders(false)
            .memoryCachePolicy(CachePolicy.ENABLED).
            diskCachePolicy(CachePolicy.ENABLED)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.diskCache(
                DiskCache.Builder()
                    .directory(LocalContext.current.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.50)
                    .build()).memoryCache( MemoryCache.Builder(LocalContext.current)
                .maxSizePercent(0.50)
                .build())
            .build()

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(gifs.images.original.url)
                .crossfade(true)
                .build(),
            contentDescription = "Gif",
            imageLoader = imageLoader,
            error = painterResource(R.drawable.ic_broken_img),
            placeholder = painterResource(R.drawable.ic_loading_img),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun LoadingScreen() {
    Image(
        modifier = Modifier.size(200.dp),
        painter = painterResource(id = R.drawable.ic_loading_img),
        contentDescription = ("Loading")
    )
}

@Composable
fun ErrorScreen() {
    Image(
        modifier = Modifier.size(200.dp),
        painter = painterResource(id = R.drawable.ic_error_img),
        contentDescription = ("Loading")
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}