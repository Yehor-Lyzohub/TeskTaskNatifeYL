package com.example.tesktasknatifeyl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tesktasknatifeyl.R
import com.example.tesktasknatifeyl.coil.CoilImageLoader
import com.example.tesktasknatifeyl.data.Data
import com.example.tesktasknatifeyl.navigation.Screen
import com.example.tesktasknatifeyl.viewmodel.GifViewModel
import com.example.tesktasknatifeyl.viewmodel.ItemUiState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(navController: NavController) {

    val gifViewModel: GifViewModel = viewModel()
    val itemUiState = gifViewModel.itemUiState

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when (itemUiState) {
            is ItemUiState.Loading -> LoadingScreen()
            is ItemUiState.Success -> RenderGifList(itemUiState.items.data, navController)
            is ItemUiState.Error -> ErrorScreen()
        }
    }
}

@Composable
fun RenderGifList(gifs: List<Data>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(items = gifs) { gif ->
            GifCard(gif, navController)
        }
    }
}

@Composable
fun GifCard(gifs: Data, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .aspectRatio(1f)
            .clickable {
                navController.navigate(
                    Screen.DetailsScreen.passUrl(
                        URLEncoder.encode(
                            gifs.images.original.url, StandardCharsets.UTF_8.toString()
                        )
                    )
                )
            },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(gifs.images.original.url)
                .crossfade(true)
                .build(),
            contentDescription = "Gif",
            imageLoader = CoilImageLoader(LocalContext.current).customImageLoader,
            error = painterResource(R.drawable.ic_broken_img),
            placeholder = painterResource(R.drawable.ic_loading_img),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
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
    HomeScreen(navController = rememberNavController())
}