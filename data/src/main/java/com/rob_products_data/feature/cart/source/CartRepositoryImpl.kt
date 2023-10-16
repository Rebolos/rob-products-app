package com.rob_products_data.feature.cart.source

import com.rob_products.models.CartItem
import com.roberto_product.local.feature.cart.source.CartLocalSource
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartLocalSource: CartLocalSource) :
    CartRepository {
    override suspend fun insertCartItem(cartItem: CartItem) {
        cartLocalSource.insertCartItem(cartItem)
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        cartLocalSource.updateCartItem(cartItem)
    }

    override suspend fun deleteCartItemById(cartId: Long) {
        cartLocalSource.deleteCartItemById(cartId = cartId)
    }

    override suspend fun updateProductCount(productName: String, countToAdd: Int): Int =
        cartLocalSource.updateProductCount(countToAdd = countToAdd, productName = productName)

    override suspend fun getAllCartItems(): List<CartItem> =
        cartLocalSource.getAllCartItems()

    override suspend fun getProductCount(productName: String): Int =
        cartLocalSource.getProductCount(productName)

    override suspend fun getTotalItemCountInCart(): Int =
        cartLocalSource.getTotalItemCountInCart()

    override suspend fun getTotalPriceAddedInCart(): Int? {
        return cartLocalSource.getTotalPriceAddedInCart()
    }

    override suspend fun deleteAllCartItems() {
        return cartLocalSource.deleteAllCartsItems()
    }
}
