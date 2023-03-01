package com.thoughtworks.androidtrain.helloworld

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HelloApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}