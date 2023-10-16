package com.example.network.di

import com.example.network.feature.products.model.ProductRemoteSource
import com.example.network.feature.products.model.ProductRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {
    @Binds
    @Singleton
    fun bindsProductRemoteSource(productRemoteSourceImpl: ProductRemoteSourceImpl): ProductRemoteSource
}
