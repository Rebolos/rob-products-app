package com.rob_products_data.feature.di

import com.rob_products_data.feature.cart.source.CartRepository
import com.rob_products_data.feature.cart.source.CartRepositoryImpl
import com.rob_products_data.feature.checkout.CheckoutRepository
import com.rob_products_data.feature.checkout.CheckoutRepositoryImpl
import com.rob_products_data.feature.product.source.ProductRepository
import com.rob_products_data.feature.product.source.impl.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindsCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindsCheckoutRepository(checkoutRepositoryImpl: CheckoutRepositoryImpl): CheckoutRepository
}
