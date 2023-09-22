package com.example.tesktasknatifeyl.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesktasknatifeyl.network.GiphyApi
import kotlinx.coroutines.launch

class GifViewModel : ViewModel() {
    var itemUiState: String by mutableStateOf("")
        private set

    init {
        getItems()
    }

    fun getItems() {
        viewModelScope.launch {
            val listResult = GiphyApi.retrofitService.getItems()
            itemUiState = "${listResult.data}"
        }
    }
}