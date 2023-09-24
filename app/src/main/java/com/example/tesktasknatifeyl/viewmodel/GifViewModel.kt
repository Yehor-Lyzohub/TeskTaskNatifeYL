package com.example.tesktasknatifeyl.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesktasknatifeyl.data.GiphyData
import com.example.tesktasknatifeyl.network.GiphyApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ItemUiState {
    data class Success(val items: GiphyData) : ItemUiState
    object Loading : ItemUiState
    object Error : ItemUiState
}

class GifViewModel : ViewModel() {
    var itemUiState: ItemUiState by mutableStateOf(ItemUiState.Loading)
        private set

    init {
        getItems()
    }

    private fun getItems() {
        viewModelScope.launch {
            itemUiState = ItemUiState.Loading

            itemUiState = try {
                ItemUiState.Success(GiphyApi.retrofitService.getItems())
            } catch (e: IOException) {
                ItemUiState.Error
            } catch (e: HttpException) {
                ItemUiState.Error
            }
        }
    }
}