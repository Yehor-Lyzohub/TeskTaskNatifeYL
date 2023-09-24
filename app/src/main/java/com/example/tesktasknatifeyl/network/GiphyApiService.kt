package com.example.tesktasknatifeyl.network

import com.example.tesktasknatifeyl.model.GiphyData
import retrofit2.http.GET

private const val API_KEY = "I33W0tqFvMypaqg782dW7ZW3SyHRPgXX"
private const val PARAMS = "limit=30&offset=0&rating=g&bundle=messaging_non_clips"

interface GiphyApiService {
    @GET("?api_key=$API_KEY&$PARAMS")
    suspend fun getGiphyData(): GiphyData
}