package com.thoughtworks.androidtrain.helloworld.di

import android.content.Context
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {
    @Singleton
    @Provides
    fun provideDataSource(@ApplicationContext context: Context): DataSource {
        return DataSourceImpl(context)
    }
}