package com.example.tesktasknatifeyl.network

import com.example.tesktasknatifeyl.data.GiphyData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.giphy.com/v1/gifs/trending/"
private const val API_KEY = "I33W0tqFvMypaqg782dW7ZW3SyHRPgXX"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GiphyApiService {
    @GET("?api_key=$API_KEY&limit=25&offset=0&rating=g&bundle=messaging_non_clips")
    suspend fun getItems(): GiphyData
}

object GiphyApi {
    val retrofitService : GiphyApiService by lazy {
        retrofit.create(GiphyApiService::class.java)
    }
}