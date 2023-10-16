package com.roberto_product.di

import com.roberto_product.local.feature.cart.source.CartLocalSource
import com.roberto_product.local.feature.cart.source.CartLocalSourceImpl
import com.roberto_product.local.feature.product.ProductLocalSource
import com.roberto_product.local.feature.product.ProductLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalSourceModule {
    @Binds
    @Singleton
    fun bindsProductLocalSource(localSourceImpl: ProductLocalSourceImpl): ProductLocalSource

    @Binds
    @Singleton
    fun bindsCartLocalSource(cartLocalSource: CartLocalSourceImpl): CartLocalSource
}
