package com.edwnmrtnz.trendingrepo.app

import android.app.Application

class TrendyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PicassoInitializer.init(context = this)
    }
}
