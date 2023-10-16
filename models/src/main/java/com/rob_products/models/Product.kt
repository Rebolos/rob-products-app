package com.rob_products.models

data class Product(
    val productId: Long,
    val productName: String,
    val productCategory: String,
    val productPrice: Int,
    val productBgColor: String,
    var productImage: Int? = null,
)
