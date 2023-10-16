package com.roberto_product.local.feature.product

import androidx.room.withTransaction
import com.rob_products.models.Product
import com.roberto_product.local.R
import com.roberto_product.local.db.RobProductDatabase
import com.roberto_product.local.feature.product.model.toDomain
import com.roberto_product.local.feature.product.model.toEntity
import javax.inject.Inject

class ProductLocalSourceImpl @Inject constructor(private val database: RobProductDatabase) :
    ProductLocalSource {
    private val productsDao = database.productsDao()
    private val listOfProductImages = listOf(
        R.drawable.shirt_one,
        R.drawable.shirt_two,
        R.drawable.shirt_three,
        R.drawable.shirt_four,
        R.drawable.shirt_five,
    )

    override suspend fun insertAllProducts(products: List<Product>) {
        database.withTransaction {
            productsDao.deleteAll()
            productsDao.insertAll(products.map { domain -> domain.toEntity(productImage = listOfProductImages[(domain.productId - 1L).toInt()]) })
        }
    }

    override suspend fun getAllProducts(): List<Product> {
        return productsDao.getAllProducts().map { entity -> entity.toDomain() }
    }

    override suspend fun getListOfProductByCategoryName(categoryName: String): List<Product> {
        return productsDao.getProductsByCategory(category = categoryName)
            .map { entity -> entity.toDomain() }
    }

    override suspend fun getDistinctCategories(): List<String> {
        return productsDao.getDistinctCategories()
    }
}
