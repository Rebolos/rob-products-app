package com.example.network.feature.products.model

import android.content.Context
import android.util.Log
import com.example.network.base.ProductListWrapper
import com.google.gson.Gson
import com.rob_product_common.utils.getJsonFromAssets
import com.rob_products.models.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProductRemoteSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson,
) : ProductRemoteSource {
    override suspend fun getListOfProducts(): List<Product> {
        val productsJson = getJsonFromAssets(context, productJson)
        val response = gson.fromJson(productsJson, ProductListWrapper::class.java)
        return response.products.map { it.toDomain() }
    }
}
