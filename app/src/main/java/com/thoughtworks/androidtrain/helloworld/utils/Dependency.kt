package com.thoughtworks.androidtrain.helloworld.utils

import android.annotation.SuppressLint
import android.content.Context
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImp

class Dependency constructor(context: Context) {
    @SuppressLint("StaticFieldLeak")
    @Volatile
    private var instance: Dependency? = null

    private var context: Context? = null

    private var dataSource: DataSource? = null

    fun getInstance(context: Context): Dependency? {
        if (instance == null) {
            synchronized(Dependency::class.java) {
                if (instance == null) {
                    instance = Dependency(context)
                    dataSource = DataSourceImp(context)
                }
            }
        }
        return instance
    }

    fun getDataSource(): DataSource? {
        return dataSource
    }
}