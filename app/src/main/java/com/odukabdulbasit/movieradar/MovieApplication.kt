package com.odukabdulbasit.movieradar

import android.app.Application
import com.odukabdulbasit.movieradar.data.AppContainer
import com.odukabdulbasit.movieradar.data.DefaultAppContainer

class MovieApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}