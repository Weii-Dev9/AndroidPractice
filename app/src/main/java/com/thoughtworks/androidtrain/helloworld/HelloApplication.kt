package com.thoughtworks.androidtrain.helloworld

import android.app.Application
import androidx.room.Room
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import com.thoughtworks.androidtrain.helloworld.utils.Dependency
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.AndroidSchedulerProvider
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.SchedulerProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HelloApplication : Application() {
    private lateinit var db: AppDatabase
    private lateinit var dependency: Dependency

    override fun onCreate() {
        super.onCreate()
        dependency = Dependency.getInstance(this)!!
        initDatabase()
    }

    fun getDb(): AppDatabase {
        return db
    }

    private fun initDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "tweet_db"
        ).build()
    }

    fun getDependency(): Dependency {
        return dependency
    }

}