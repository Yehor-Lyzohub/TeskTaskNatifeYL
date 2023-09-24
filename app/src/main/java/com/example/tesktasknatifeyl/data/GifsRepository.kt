package com.example.tesktasknatifeyl.data

import com.example.tesktasknatifeyl.model.GiphyData
import com.example.tesktasknatifeyl.network.GiphyApiService

interface GifsRepository {
    suspend fun getGiphyData(): GiphyData
}

class NetworkGifsRepository(
    private val giphyApiService: GiphyApiService
) : GifsRepository {
    override suspend fun getGiphyData(): GiphyData {
        return giphyApiService.getGiphyData()
    }
}