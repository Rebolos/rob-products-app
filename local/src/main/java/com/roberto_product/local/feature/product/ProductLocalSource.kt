package com.roberto_product.local.feature.product

import com.rob_products.models.Product

interface ProductLocalSource {

    suspend fun insertAllProducts(products: List<Product>)

    suspend fun getAllProducts(): List<Product>

    suspend fun getListOfProductByCategoryName(categoryName: String): List<Product>

    suspend fun getDistinctCategories(): List<String>
}
