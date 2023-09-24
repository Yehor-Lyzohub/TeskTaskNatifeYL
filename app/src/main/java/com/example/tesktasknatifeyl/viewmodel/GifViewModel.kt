package com.example.tesktasknatifeyl.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tesktasknatifeyl.GiphyApplication
import com.example.tesktasknatifeyl.data.GifsRepository
import com.example.tesktasknatifeyl.model.GiphyData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface GifItemUiState {
    data class Success(val items: GiphyData) : GifItemUiState
    object Loading : GifItemUiState
    object Error : GifItemUiState
}

class GifViewModel(
    private val gifsRepository: GifsRepository
) : ViewModel() {
    var gifItemUiState: GifItemUiState by mutableStateOf(GifItemUiState.Loading)
        private set

    init {
        getGifItems()
    }

    fun getGifItems() {
        viewModelScope.launch {
            gifItemUiState = GifItemUiState.Loading
            gifItemUiState = try {
                GifItemUiState.Success(gifsRepository.getGiphyData())
            } catch (e: IOException) {
                GifItemUiState.Error
            } catch (e: HttpException) {
                GifItemUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GiphyApplication)
                val gifsRepository = application.container.gifsRepository
                GifViewModel(gifsRepository = gifsRepository)
            }
        }
    }
}