package com.edwnmrtnz.trendingrepo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TrendyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PicassoInitializer.init(context = this)
    }
}
