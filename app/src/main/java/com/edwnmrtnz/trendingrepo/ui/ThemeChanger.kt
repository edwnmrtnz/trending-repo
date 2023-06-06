package com.edwnmrtnz.trendingrepo.ui

import androidx.appcompat.app.AppCompatDelegate

object ThemeChanger {
    private var isNight = false

    fun set() {
        isNight = false
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun change() {
        isNight = !isNight
        AppCompatDelegate.setDefaultNightMode(
            if (isNight) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
