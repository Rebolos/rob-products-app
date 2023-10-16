package com.rob_products_data.feature.checkout

import com.rob_products.models.CartItem

data class Order(
    val listOfCarts: List<CartItem>,
    val orderId: String,
    val totalPrice: Int,
)
