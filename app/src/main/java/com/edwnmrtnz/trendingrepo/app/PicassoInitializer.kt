package com.edwnmrtnz.trendingrepo.app

import android.content.Context
import android.util.Log
import com.edwnmrtnz.trendingrepo.BuildConfig
import com.squareup.picasso.Picasso

object PicassoInitializer {
    fun init(context: Context) {
        val picasso = Picasso.Builder(context.applicationContext)
            .listener { _, uri, exception ->
                Log.e("Picasso", "Failed to load $uri > ${exception.message}")
            }
            .loggingEnabled(BuildConfig.DEBUG)
            .indicatorsEnabled(BuildConfig.DEBUG)
            .build()

        Picasso.setSingletonInstance(picasso)
    }
}
