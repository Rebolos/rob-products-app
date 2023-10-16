package com.rob_products_data.feature.checkout

import com.rob_products.models.CartItem

interface CheckoutRepository {
    suspend fun createAndSaveOrder(carts: List<CartItem>, total: Int): Pair<String?, Boolean>
}
