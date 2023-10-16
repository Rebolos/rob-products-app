package com.example.network.feature.products.model

import com.example.network.base.BaseResponseNew
import com.rob_products.models.Product

interface ProductRemoteSource {
    suspend fun getListOfProducts(): List<Product>
}
