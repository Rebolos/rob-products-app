package com.example.network.base

import com.example.network.feature.products.model.ProductDTO
import com.google.gson.annotations.SerializedName

data class ProductListWrapper(
    @SerializedName("products") val products: List<ProductDTO>
)