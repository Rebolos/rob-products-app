package com.roberto_product.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.roberto_product.local.feature.product.model.CartItemEntity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)

    @Query("UPDATE ${CartItemEntity.TABLE_NAME} SET product_count = product_count + :countToAdd WHERE product_name = :productName")
    suspend fun updateProductCount(productName: String, countToAdd: Int): Int

    @Query("DELETE FROM ${CartItemEntity.TABLE_NAME} WHERE cart_item_id = :cartId")
    suspend fun deleteCartItemById(cartId: Long)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItems(): List<CartItemEntity>

    @Query("SELECT product_count FROM ${CartItemEntity.TABLE_NAME} WHERE product_name = :productName")
    suspend fun getProductCount(productName: String): Int

    @Query("SELECT SUM(product_count) FROM cart_items")
    suspend fun getTotalItemCountInCart(): Int

    @Query("SELECT IFNULL(SUM(product_price), 0) FROM cart_items")
    suspend fun getTotalPriceInCart(): Int?

    @Query("DELETE FROM ${CartItemEntity.TABLE_NAME}")
    suspend fun deleteAllCartItems()
}
