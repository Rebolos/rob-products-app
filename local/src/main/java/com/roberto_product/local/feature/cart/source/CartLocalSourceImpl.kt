package com.roberto_product.local.feature.cart.source

import com.rob_products.models.CartItem
import com.roberto_product.local.db.RobProductDatabase
import com.roberto_product.local.feature.product.model.toDomain
import com.roberto_product.local.feature.product.model.toEntity
import javax.inject.Inject

class CartLocalSourceImpl @Inject constructor(private val database: RobProductDatabase) :
    CartLocalSource {
    private val cartItemDao = database.cartDao()
    override suspend fun insertCartItem(cartItem: CartItem) {
        return cartItemDao.insertCartItem(cartItem = cartItem.toEntity())
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        return cartItemDao.updateCartItem(cartItem = cartItem.toEntity())
    }

    override suspend fun deleteCartItemById(cartId: Long) {
        return cartItemDao.deleteCartItemById(cartId = cartId)
    }

    override suspend fun updateProductCount(productName: String, countToAdd: Int): Int {
        return cartItemDao.updateProductCount(productName = productName, countToAdd = countToAdd)
    }

    override suspend fun getAllCartItems(): List<CartItem> {
        return cartItemDao.getAllCartItems().map { domain -> domain.toDomain() }
    }

    override suspend fun getProductCount(productName: String): Int {
        return cartItemDao.getProductCount(productName = productName)
    }

    override suspend fun getTotalItemCountInCart(): Int {
        return cartItemDao.getTotalItemCountInCart()
    }

    override suspend fun getTotalPriceAddedInCart(): Int? {
        return cartItemDao.getTotalPriceInCart()
    }

    override suspend fun deleteAllCartsItems() {
        return cartItemDao.deleteAllCartItems()
    }
}
