package com.example.tesktasknatifeyl.data

import com.example.tesktasknatifeyl.network.GiphyApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val gifsRepository: GifsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.giphy.com/v1/gifs/trending/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : GiphyApiService by lazy {
        retrofit.create(GiphyApiService::class.java)
    }

    override val gifsRepository: GifsRepository by lazy {
        NetworkGifsRepository(retrofitService)
    }
}