package com.thoughtworks.androidtrain.helloworld.di

import android.content.Context
import com.thoughtworks.androidtrain.helloworld.compose.ComposeViewModel
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_vm")
    fun provideViewModule(@ApplicationContext context: Context) =
        ComposeViewModel(dataSource = DataSourceImpl(context))
}
