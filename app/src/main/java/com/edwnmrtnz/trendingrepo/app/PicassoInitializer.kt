package com.edwnmrtnz.trendingrepo.app

import android.content.Context
import android.util.Log
import com.edwnmrtnz.trendingrepo.BuildConfig
import com.squareup.picasso.Picasso

object PicassoInitializer {
    fun init(context: Context) {
        val picasso = Picasso.Builder(context.applicationContext)
            .listener { _, uri, exception ->
                Log.e("Picasso", "Failed to load $uri because $exception")
            }
            .indicatorsEnabled(BuildConfig.DEBUG)
            .build()
        Picasso.setSingletonInstance(picasso)
    }
}
