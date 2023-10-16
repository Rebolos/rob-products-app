package com.rob_products.models

data class CartItem(
    val id: Long? = null,
    val productName: String,
    val productCount: Int? = null,
    val productPrice: Int,
    val color: String,
)
