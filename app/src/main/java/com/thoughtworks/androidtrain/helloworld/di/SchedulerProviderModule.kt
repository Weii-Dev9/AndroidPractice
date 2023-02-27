package com.thoughtworks.androidtrain.helloworld.di

import com.thoughtworks.androidtrain.helloworld.utils.schedulers.AndroidSchedulerProvider
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SchedulerProviderModule {
    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AndroidSchedulerProvider()
    }
}