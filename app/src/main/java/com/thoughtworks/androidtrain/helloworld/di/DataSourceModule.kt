package com.thoughtworks.androidtrain.helloworld.di

import android.content.Context
import androidx.room.Room
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImpl
import com.thoughtworks.androidtrain.helloworld.data.source.local.LocalStorage
import com.thoughtworks.androidtrain.helloworld.data.source.local.LocalStorageImpl
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import com.thoughtworks.androidtrain.helloworld.data.source.remote.RemoteData
import com.thoughtworks.androidtrain.helloworld.data.source.remote.RemoteDataImpl
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
    fun provideDataSource(localStorage: LocalStorage, remoteData: RemoteData): DataSource =
        DataSourceImpl(localStorage, remoteData)

    @Singleton
    @Provides
    fun provideLocalDataStorage(database: AppDatabase): LocalStorage = LocalStorageImpl(database)

    @Singleton
    @Provides
    fun provideRemoteData(): RemoteData = RemoteDataImpl()
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "db_tweet").build()
    }
}
