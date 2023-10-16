package com.roberto_product.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roberto_product.local.dao.CartDao
import com.roberto_product.local.dao.ProductDao
import com.roberto_product.local.feature.product.model.CartItemEntity
import com.roberto_product.local.feature.product.model.ProductEntity
import com.roberto_product.local.utils.DatabaseHelper

@Database(entities = [ProductEntity::class, CartItemEntity::class], version = 8)
abstract class RobProductDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductDao
    abstract fun cartDao(): CartDao

    companion object {
        const val DATABASE_NAME = "product.db"

        // We handle singleton implementation here via dagger, instead of manually handling it.
        fun createInstance(helper: DatabaseHelper): RobProductDatabase {
            return buildDatabase(helper)
        }

        private fun buildDatabase(helper: DatabaseHelper): RobProductDatabase {
            return helper.createDatabase(
                name = DATABASE_NAME,
                kClass = RobProductDatabase::class,
            )
        }
    }
}
