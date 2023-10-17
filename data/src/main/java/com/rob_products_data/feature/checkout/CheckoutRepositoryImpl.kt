package com.rob_products_data.feature.checkout

import com.rob_products.models.CartItem
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(private val orderJsonFileStorage: OrderJsonFileStorage) :
    CheckoutRepository {
    override suspend fun createAndSaveOrder(carts: List<CartItem>, total: Int): Pair<String?, Boolean> {
        return orderJsonFileStorage.saveOrderToJson(listOfCartItems = carts, total = total)
    }
}
