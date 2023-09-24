package com.example.tesktasknatifeyl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tesktasknatifeyl.R
import com.example.tesktasknatifeyl.coil.CoilImageLoader
import com.example.tesktasknatifeyl.model.Data
import com.example.tesktasknatifeyl.navigation.Screen
import com.example.tesktasknatifeyl.viewmodel.GifItemUiState
import com.example.tesktasknatifeyl.viewmodel.GifViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(navController: NavController) {

    val gifViewModel: GifViewModel = viewModel(factory = GifViewModel.Factory)
    val gifItemUiState = gifViewModel.gifItemUiState
    val refreshAction = gifViewModel::getGifItems

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        when (gifItemUiState) {
            is GifItemUiState.Loading -> LoadingScreen()
            is GifItemUiState.Success -> RenderGifList(gifItemUiState.items.data, navController)
            is GifItemUiState.Error -> ErrorScreen(refreshAction)
        }
    }
}

@Composable
fun WelcomeSection() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(R.string.welcome),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RenderGifList(gifs: List<Data>, navController: NavController) {
    Column {
        WelcomeSection()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(items = gifs) { gif ->
                GifCard(gif, navController)
            }
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
            contentDescription = stringResource(R.string.gif_description),
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
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.ic_loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(refreshAction: () -> Unit) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.ic_error_img),
            contentDescription = stringResource(R.string.error_description)
        )
        Text(text = stringResource(R.string.failed_to_load))
        Button(onClick = refreshAction) {
            Text(text = stringResource(R.string.refresh))
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}