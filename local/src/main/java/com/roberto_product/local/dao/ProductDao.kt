package com.roberto_product.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roberto_product.local.feature.product.model.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(list: List<ProductEntity>)

    @Query("DELETE FROM ${ProductEntity.TABLE_NAME}")
   abstract suspend fun deleteAll()

    @Query("SELECT * FROM ${ProductEntity.TABLE_NAME}")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM ${ProductEntity.TABLE_NAME} WHERE product_category = :category")
    suspend fun getProductsByCategory(category: String): List<ProductEntity>

    @Query("SELECT DISTINCT product_category FROM ${ProductEntity.TABLE_NAME}")
    suspend fun getDistinctCategories(): List<String>
}
