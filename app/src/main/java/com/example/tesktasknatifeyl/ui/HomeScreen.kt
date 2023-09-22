package com.example.tesktasknatifeyl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tesktasknatifeyl.viewmodel.GifViewModel

@Composable
fun HomeScreen() {

    val gifViewModel: GifViewModel = viewModel()
    val items = gifViewModel.itemUiState

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        RenderItems(items)
    }
}

@Composable
fun RenderItems(items: String) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = items)
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}