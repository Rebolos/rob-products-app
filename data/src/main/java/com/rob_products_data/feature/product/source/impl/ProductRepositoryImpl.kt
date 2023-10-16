package com.rob_products_data.feature.product.source.impl

import android.util.Log
import com.example.network.feature.products.model.ProductRemoteSource
import com.rob_products.models.Product
import com.rob_products_data.feature.product.source.ProductRepository
import com.roberto_product.local.feature.product.ProductLocalSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteSource: ProductRemoteSource,
    private val localSource: ProductLocalSource,
) : ProductRepository {
    override suspend fun getListOfProduct(): List<Product> {
        val listOfProducts = remoteSource.getListOfProducts()
        localSource.insertAllProducts(products = listOfProducts)
        return localSource.getAllProducts()
    }

    override suspend fun getListOfProductByCategoryName(categoryName: String): List<Product> {
        return localSource.getListOfProductByCategoryName(categoryName)
    }

    override suspend fun getDistinctCategories(): List<String> {
        return localSource.getDistinctCategories()
    }
}
