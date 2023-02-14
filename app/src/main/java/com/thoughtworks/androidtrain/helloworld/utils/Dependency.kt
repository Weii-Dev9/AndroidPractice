package com.thoughtworks.androidtrain.helloworld.utils

import android.annotation.SuppressLint
import android.content.Context
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImpl
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.AndroidSchedulerProvider
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.SchedulerProvider

class Dependency constructor(context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: Dependency? = null

        fun getInstance(context: Context?): Dependency? {
            if (instance == null) {
                synchronized(Dependency::class.java) {
                    if (instance == null) {
                        instance =
                            context?.let { Dependency(it) }
                    }
                }
            }
            return instance
        }
    }

    private var context: Context? = null
    private var dataSource: DataSourceImpl? = null
    private val schedulerProvider = AndroidSchedulerProvider()


    init {
        this.context = context
        dataSource = DataSourceImpl(context)
    }


    fun getDataSource(): DataSource? {
        return dataSource
    }

    fun getSchedulerProvider(): SchedulerProvider {
        return schedulerProvider
    }
}