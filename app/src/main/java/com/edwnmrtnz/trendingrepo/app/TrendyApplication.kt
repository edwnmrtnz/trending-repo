package com.edwnmrtnz.trendingrepo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TrendyApplication : Application() {
    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
    override fun onCreate() {
        super.onCreate()
        PicassoInitializer.init(context = this)
    }
}
