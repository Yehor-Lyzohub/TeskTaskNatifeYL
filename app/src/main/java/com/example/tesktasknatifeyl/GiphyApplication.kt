package com.example.tesktasknatifeyl

import android.app.Application
import com.example.tesktasknatifeyl.data.AppContainer
import com.example.tesktasknatifeyl.data.DefaultAppContainer

class GiphyApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}