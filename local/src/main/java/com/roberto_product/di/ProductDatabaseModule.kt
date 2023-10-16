package com.roberto_product.di

import com.roberto_product.local.db.RobProductDatabase
import com.roberto_product.local.utils.DatabaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object ProductDatabaseModule {
    @Provides
    @Singleton
    fun providesRobProductDatabase(helper: DatabaseHelper): RobProductDatabase {
        return RobProductDatabase.createInstance(helper)
    }
}
