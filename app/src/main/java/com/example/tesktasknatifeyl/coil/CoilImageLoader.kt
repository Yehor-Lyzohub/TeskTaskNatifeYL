package com.example.tesktasknatifeyl.coil

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy

class CoilImageLoader(context: Context) {
    val customImageLoader = ImageLoader.Builder(context)
        .respectCacheHeaders(false)
        .memoryCachePolicy(CachePolicy.ENABLED).diskCachePolicy(CachePolicy.ENABLED)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.diskCache(
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.50)
                .build()
        ).memoryCache(
            MemoryCache.Builder(context)
                .maxSizePercent(0.50)
                .build()
        )
        .build()
}