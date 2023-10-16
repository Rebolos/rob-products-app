package com.example.network.feature.products.model

import com.google.gson.annotations.SerializedName
import com.rob_products.models.Product
import kotlin.math.roundToInt

data class ProductDTO(
    @SerializedName("id") val productId: String,
    @SerializedName("name") val productName: String,
    @SerializedName("category") val productCategory: String,
    @SerializedName("price") val productPrice: Float,
    @SerializedName("bgColor") val productBgColor: String,
)

fun ProductDTO.toDomain() = Product(
    productId = productId.removePrefix("p_").toLong(),
    productName = productName,
    productCategory = productCategory,
    productBgColor = productBgColor,
    productPrice = productPrice.roundToInt(),
)
