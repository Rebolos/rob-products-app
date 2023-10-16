package com.roberto_product.local.feature.cart.source

import com.rob_products.models.CartItem

interface CartLocalSource {
    suspend fun insertCartItem(cartItem: CartItem)

    suspend fun updateCartItem(cartItem: CartItem)

    suspend fun deleteCartItemById(cartId: Long)
    suspend fun updateProductCount(productName: String, countToAdd: Int): Int

    suspend fun getAllCartItems(): List<CartItem>

    suspend fun getProductCount(productName: String): Int
    suspend fun getTotalItemCountInCart(): Int
    suspend fun getTotalPriceAddedInCart(): Int?
    suspend fun deleteAllCartsItems()
}
