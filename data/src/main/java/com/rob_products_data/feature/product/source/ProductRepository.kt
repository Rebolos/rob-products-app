package com.rob_products_data.feature.product.source

import com.rob_products.models.Product

interface ProductRepository {

    suspend fun getListOfProduct(): List<Product>

    suspend fun getListOfProductByCategoryName(categoryName: String): List<Product>
    suspend fun getDistinctCategories(): List<String>
}
