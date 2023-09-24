package com.example.tesktasknatifeyl.model

import kotlinx.serialization.Serializable

@Serializable
data class GiphyData(
    val data: List<Data>
)

@Serializable
data class Data(
    val id: String,
    val images: Images
)

@Serializable
data class Images(
    val original: Original
)

@Serializable
data class Original(
    val url: String
)